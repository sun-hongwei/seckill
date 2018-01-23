package org.seckill.web;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by sun on 2017/11/7.
 */
@RequestMapping("/seckill")
@Controller
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    /**
     * 获取秒杀产品的列表页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Seckill> seckillList = seckillService.getSeckillList();
        model.addAttribute("list", seckillList);
        return "list";
    }

    /**
     * 根据产品ID查询产品
     *
     * @param seckillId
     * @param model
     * @return
     */
    @RequestMapping(value = "/{seckillId}/detaile", method = RequestMethod.GET)
    public String detaile(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getSeckillById(seckillId);
        if (seckill == null) {
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "detaile";
    }

    /**
     * 获取秒杀的权限
     *
     * @param seckillId
     * @return
     */
    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(Long seckillId) {
        SeckillResult<Exposer> seckillResult;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        try {
            seckillResult = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            seckillResult = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return seckillResult;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone", required = false) Long userPhone) {
        SeckillResult<SeckillExecution> executionSeckillResult;

        if (userPhone == null){
            return new SeckillResult<SeckillExecution>(false,"未注册");
        }
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
            executionSeckillResult = new SeckillResult<SeckillExecution>(true, seckillExecution);
        } catch (RepeatKillException e1){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillStateEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(false,seckillExecution);
        }catch (SeckillCloseException e2){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillStateEnum.END);
            return new SeckillResult<SeckillExecution>(false,seckillExecution);
        }catch (Exception e) {
            executionSeckillResult = new SeckillResult<SeckillExecution>(false, e.getMessage());
        }
        return executionSeckillResult;
    }

    @RequestMapping(value = "/seckill/time")
    public SeckillResult time(){
        Date date = new Date();
        return new SeckillResult(true,date);
    }
}
