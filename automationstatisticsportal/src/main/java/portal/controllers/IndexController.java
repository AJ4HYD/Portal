package portal.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import portal.domain.TestRun;
import portal.repositories.PhaseRepository;
import portal.repositories.TestRunRepository;
import portal.services.AdminService;
import portal.services.MetricsService;

@Controller
public class IndexController {

    private MetricsService metricsService;
    private AdminService adminService;

    @Autowired
    public void setMetricsService(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    String index(Model model) {
        model.addAttribute("metrics", metricsService.getMetricsForHomePage());
        model.addAttribute("tools", metricsService.getToolMetricsForHomePage());
        model.addAttribute("results", metricsService.getPassFailMetricsForHomePage());
        model.addAttribute("datatypes", metricsService.getDataTypeMetricsForHomePage());
        model.addAttribute("phase", metricsService.getPhaseMetricsForHomePage());
        model.addAttribute("phaseList", metricsService.displayPhaseMetricsForHomePage());
        return "index";
    }

    @RequestMapping(value = "/between/{start}/and/{end}", method = RequestMethod.GET)
    public String index(Model model,
                        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {
        model.addAttribute("metrics", metricsService.getMetricsForHomePage(start, end));
        model.addAttribute("tools", metricsService.getToolMetricsForHomePage(start, end));
        model.addAttribute("results", metricsService.getPassFailMetricsForHomePage(start, end));
        model.addAttribute("datatypes", metricsService.getDataTypeMetricsForHomePage(start, end));
        model.addAttribute("phase", metricsService.getPhaseMetricsForHomePage(start, end));
        model.addAttribute("phaseList", metricsService.displayPhaseMetricsForHomePage(start, end));
        return "index";
    }

    @GetMapping("/portal/generators")
    public String generators() {
        return "generators";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/portal/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/portal/admin/reset")
    public String reset() {
        adminService.resetStatisticsToZero();
        return "admin";
    }
}