package com.exmp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Check if the user is logged in (ensure session is valid)
      
        return "dashboard";  // Return the dashboard page (dashboard.html)
    }
}
