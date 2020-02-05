/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inc.kraken.isisoap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 *
 * @author carlosndiaye
 */
@Controller
public class IndexController {

    @GetMapping("/api")
    public String welcome() {
        return "index";
    }

}
