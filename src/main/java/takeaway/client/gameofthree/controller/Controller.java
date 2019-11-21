package takeaway.client.gameofthree.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import takeaway.client.gameofthree.dto.Player;

@RestController
@Validated
public class Controller {

	@RequestMapping(value="/index", method = RequestMethod.GET)
    public String showLoginPage(Player player){
		
		
        return "startGame";
    }
}