package com.example.amazonclone.Controller;

import com.example.amazonclone.ApiResponse.ApiResponse;
import com.example.amazonclone.Model.User;
import com.example.amazonclone.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity getUsers(){
        return ResponseEntity.status(200).body(userService.getUsers());
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        userService.addUser(user);
        return ResponseEntity.status(201).body(new ApiResponse("user added"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable String id){
       boolean isDeleted = userService.removeUser(id);

       if(isDeleted){
           return ResponseEntity.status(201).body(new ApiResponse("user deleted"));
       }else return ResponseEntity.status(400).body(new ApiResponse("user not deleted"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable String id, @RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = userService.updateUser(id, user);
        if(isUpdated){
            return ResponseEntity.status(201).body(new ApiResponse("user updated"));
        }else return ResponseEntity.status(400).body(new ApiResponse("user not updated"));
    }

    @PutMapping("/buy-product/{userid}/{productid}/{merchantid}")
    public ResponseEntity buyProduct(@PathVariable String userid, @PathVariable String productid, @PathVariable String merchantid) {
        String checker = userService.buyProduct(userid, productid, merchantid);

        if (checker.equals("done")) {
            return ResponseEntity.status(200).body(new ApiResponse("product purchased"));
        } else if (checker.equals("pm")) {
            return ResponseEntity.status(404).body(new ApiResponse("user id does not exist"));
        } else if (checker.equals("pu")) {
            return ResponseEntity.status(404).body(new ApiResponse("merchant id does not exist"));
        } else if (checker.equals("mu")) {
            return ResponseEntity.status(404).body(new ApiResponse("product id does not exist"));
        } else if (checker.equals("dones-")) {return ResponseEntity.status(400).body(new ApiResponse("out of stock"));
        } else if (checker.equals("pmub-")) {return ResponseEntity.status(400).body(new ApiResponse("balance insufficient"));
        } else if (checker.equals("m")){return ResponseEntity.status(404).body(new ApiResponse("product id and user id does not exist"));
        } else if (checker.equals("p")) {return ResponseEntity.status(404).body(new ApiResponse("user id and merchant id does not exist"));
        } else if (checker.equals("u")){return ResponseEntity.status(404).body(new ApiResponse("merchant id and product id does not exist"));
        }else if (checker.equals("")){return ResponseEntity.status(404).body("user id & product id & merchant id does not exist");
        }else return ResponseEntity.status(400).body(new ApiResponse(checker));
    }

    //ex1
    @PutMapping("/luckyDrawCashBack")
    public ResponseEntity luckyDrawCashBack() {
        User u = userService.luckyDrawCashBack();

        if (u != null) {
            return ResponseEntity.status(200).body(u);
        }else return ResponseEntity.status(400).body(new ApiResponse("no cashback for users because there is no buyers saved in the system"));
    }

    //ex2
    @PutMapping("/addDiscount/{userid}/{productid}/{discount}")
    public ResponseEntity addDiscount(@PathVariable String userid, @PathVariable String productid, @PathVariable double discount) {
        int num = userService.addDiscount(userid,productid,discount);
        return switch (num){
            case 0 -> ResponseEntity.status(200).body(new ApiResponse("discount added"));
            case 1 -> ResponseEntity.status(404).body(new ApiResponse("user id does not exist"));
            case 2 -> ResponseEntity.status(404).body(new ApiResponse("user role is not 'Admin"));
            case 3 -> ResponseEntity.status(404).body(new ApiResponse("product id does not exist"));
            default -> ResponseEntity.status(400).body(new ApiResponse("discount not added"));
        };
    }

    @PutMapping("/generateGiftCard/{userid}/{value}")
    public ResponseEntity generateGiftCard(@PathVariable String userid, @PathVariable int value) {
        int num = userService.generateGiftCard(userid,value);
        return switch (num){
            case 0 -> ResponseEntity.status(200).body(new ApiResponse("Gift card created"));
            case 1 -> ResponseEntity.status(404).body(new ApiResponse("user id does not exist"));
            case 2 -> ResponseEntity.status(404).body(new ApiResponse("user insufficient balance"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Gift card not created"));
        };

    }

    @PutMapping("/redeemGiftCard/{userid}/{code}")
    public ResponseEntity redeemGiftCard(@PathVariable String userid, @PathVariable String code) {
        int num = userService.redeemGiftCard(userid,code);
        return switch (num){
            case 0 -> ResponseEntity.status(200).body(new ApiResponse("Gift card redeemed"));
            case 1 -> ResponseEntity.status(404).body(new ApiResponse("user id does not exist"));
            case 2 -> ResponseEntity.status(404).body(new ApiResponse("Gift card does not exist"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Gift card not redeemed"));
        };
    }

    @PutMapping("/inviteRewards/{newuserid}/{hostuserid}")
    public ResponseEntity inviteRewards(@PathVariable String newuserid, @PathVariable String hostuserid) {
        int num = userService.inviteRewards(newuserid,hostuserid);
        return switch (num){
            case 0 -> ResponseEntity.status(200).body(new ApiResponse("Congrats 25 Riyals added to your account"));
            case 1 -> ResponseEntity.status(404).body(new ApiResponse("host user id does not exist"));
            case 2 -> ResponseEntity.status(404).body(new ApiResponse("new user if does not exist"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Rewards not added"));
        };
    }


}