package com.example.bookapp.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.logging.Logger;

//@ControllerAdvice
public class ErrorController {
    
    private static final Logger logger = Logger.getLogger(ErrorController.class.getName());

//    @ExceptionHandler(AccessDeniedException.class)
//    public String accessDenied(Exception ex) {
//        logger.warning(ex.getLocalizedMessage());
//        return "redirect:/access-denied";
//    }
    
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle(Exception ex) {
        return "redirect:/404";
    }
    
    @GetMapping("/404")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String pageNotFound() {
        return "404";
    }
    
    // for 403 access denied page
//    @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ModelAndView accesssDenied() {
//        
//        ModelAndView model = new ModelAndView();
//        
//        // check if user is login
////        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////        if (!(auth instanceof AnonymousAuthenticationToken)) {
////            CustomUser user = (CustomUser) auth.getPrincipal();
////            model.addObject("username", user.getUsername());
////        }
//        
//        model.setViewName("access-denied");
//        return model;
//        
//    }
    
    
    //    @ExceptionHandler(Throwable.class)
    //    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    //    public String exception(final Throwable throwable, final Model model) {
    //        // logger.error("Exception during execution of SpringSecurity application",
    //        // throwable);
    //        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
    //        model.addAttribute("errorMessage", errorMessage);
    //        return "error";
    //    }
    
}

