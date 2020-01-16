package trade.gdgrain.grain_test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import trade.gdgrain.grain_test.pojo.Grain;
import trade.gdgrain.grain_test.pojo.MyGrain;
import trade.gdgrain.grain_test.pojo.Success;
import trade.gdgrain.grain_test.pojo.SuccessGrain;
import trade.gdgrain.grain_test.service.GrainService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class GrainController {


    @Autowired
    public GrainService grainService;


    /**
     * 查询出指定id的信息
     * @param id
     * @param uuid
     * @param model
     * @return
     */
    @RequestMapping("/one/{id}")
    public String showPage(
            @PathVariable("id") Integer id,
            @RequestParam("uuid") Long uuid,
            Model model){
        Grain grain = grainService.selectById(id);
        model.addAttribute("grain",grain);
        model.addAttribute("uuid",uuid);
        return "one";
    }

    /**
     * 首页跳转显示
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String showAll(Model model){
        model.addAttribute("grainList",grainService.getGrainList());
        return "show";
    }

    /**
     * 动态刷新
     * @return
     */
    @GetMapping("/show")
    @ResponseBody
    public List<MyGrain> show(){
        return grainService.getGrainList();
    }

    /**
     * 应价按钮
     * @param id
     * @param newPrice
     * @param uuid 页面生成的唯一表示，加入cookie中
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/submit")
    public String submit(
            @RequestParam("id") Integer id,

            @RequestParam("newPrice") Integer newPrice,
            @RequestParam("uuid") Long uuid,
            HttpServletResponse response,
            HttpServletRequest request,
            Model model){
        //model.addAttribute("id",id);
        Boolean sign = false;
        grainService.updataRrice(id,newPrice);
        Long cookieValue = uuid;

        //判断是否有UUID的cookie
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("uuid")) {
                    sign =true;
                    cookieValue = Long.valueOf(cookie.getValue());
                }
            }
        }
        if(!sign){
            Cookie uuidCookie = new Cookie("uuid",uuid+"");
            response.addCookie(uuidCookie);
        }
        grainService.joinList(id,cookieValue,newPrice);
        /*Cookie cookie = new Cookie("uuid",uuid+"");
        response.addCookie(cookie);
        model.addAttribute("sign",1);*/
        return "redirect:/";
    }

    /**
     * 应价后刷新时间
     * @param id
     */
    @GetMapping("/changTime")
    @ResponseBody
    public void changTime(@RequestParam("id") Integer id){
        grainService.changTime(id);
    }

    /**
     * 添加应价过的数据
     * @param id
     * @param uuid
     * @param price
     */
    @GetMapping("/joinList")
    @ResponseBody
    public void joinList(Integer id,Long uuid,Integer price){
        grainService.joinList(id,uuid,price);
    }


    /**
     * 获取出应价过的信息
     * @param request
     * @return
     */
    @RequestMapping("/showSuccessList")
    @ResponseBody
    public List<SuccessGrain> showSuccessList(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String cookieValue = null;
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("uuid")) {
                    cookieValue = cookie.getValue();
                }
            }
        }
        //System.out.println(cookieValue);
        Long uuidLong = Long.valueOf(cookieValue);
        List<SuccessGrain> joinList = grainService.getJoinList(uuidLong);
        return joinList;
    }

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping("/checkMessage")
    @ResponseBody
    public List<Success> checkMessage(HttpServletRequest request){
        Long uuid = null;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("uuid")){
                    uuid = Long.valueOf(cookie.getValue());
                }
            }
            return grainService.checkMessage(uuid);
        }
        return null;
    }

}
