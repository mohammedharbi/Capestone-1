package com.example.amazonclone.Service;

import com.example.amazonclone.Model.Merchant;
import com.example.amazonclone.Model.MerchantStock;
import com.example.amazonclone.Model.Product;
import com.example.amazonclone.Model.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class UserService {

    ArrayList<User> users = new ArrayList<>();
    ArrayList<User> buyersUsers = new ArrayList<>();
    ArrayList<String> giftsCardsGenerated = new ArrayList<>();

    private final ProductService productService;
    private final MerchantService merchantService;
    private final MerchantStockService merchantStockService;

    public void addUser(User user) {
        users.add(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean removeUser(String id) {

        for (User user : users) {
            if (user.getId().equals(id)) {
                users.remove(user);
                return true;
            }
        }
        return false;
    }

    public boolean updateUser(String id,User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    /*
Create endpoint where user can buy a product directly
• this endpoint should accept user id, product id, merchant id.
• check if all the given ids are valid or not
• check if the merchant has the product in stock or return bad request.
• reduce the stock from the MerchantStock.
• deducted the price of the product from the user balance.
• if balance is less than the product price returns bad request.
     */

    public String buyProduct(String userid, String productid, String merchantid) {
        String checker = "";
        double balance_user=0.0;
        double price=0.0;

        for (Product p: productService.getProducts()){
            if (p.getId().equals(productid)){
                checker += "p";
            }
        }
        for (Merchant m: merchantService.getMerchants()){
            if (m.getId().equals(merchantid)){
                checker += "m";
            }
        }
        for (User u: users){
            if (u.getId().equals(userid)){
                checker += "u";
            }
        }
        if (checker.equals("pmu")){
            for (Product p: productService.getProducts()){
                if (p.getId().equals(productid)){
                    for (User u: users){
                        if (u.getId().equals(userid)){
                                for (MerchantStock m : merchantStockService.getMerchantss()){
                                    if (m.getMerchantid().equals(merchantid)){
                                        if (u.getBalance()>=p.getPrice()){
                                        if (m.getStock()>0){
                                            m.setStock(m.getStock()-1);
                                            u.setBalance(u.getBalance()-p.getPrice());
                                            checker = "done";
                                            price = p.getPrice();
                                            u.getPurshseHistory().add(price);
                                            buyersUsers.add(u);
                                        }else checker += "s-"; // pmus- means out of stock
                                    } else checker += "b-"; // pmub- user insufficient balance
                                }
                            }
                        }
                    }
                }
            }

        }
        return checker;
    }

    //ex 1
    public User luckyDrawCashBack(){
        Random random = new Random();
        if (!buyersUsers.isEmpty()){ // as long as buyersUser array list isn't empty begin the method
            int random_winner = random.nextInt(buyersUsers.size()); // index of the winner in the luck draw
            double min = buyersUsers.get(random_winner).getPurshseHistory().get(0); // assuming the product with the index 0 is the smallest price
            for (Double prices:buyersUsers.get(random_winner).getPurshseHistory()){ // loop to check the smallest product price saved in PurshseHistory
                if (prices<min){min = prices;}
            }
            for (User u: users){
                if (u.getId().equals(buyersUsers.get(random_winner).getId())){
                    u.setBalance(u.getBalance()+min);
                    return u;
                }
            }
        }
        return null;
    }

    //ex2
    public int addDiscount(String userid,String productid, double discount) {
        for (User u : users) {
            if (u.getId().equals(userid)) {
                if (u.getRole().equalsIgnoreCase("Admin")) {
                    for (Product p : productService.getProducts()) {
                        if (p.getId().equals(productid)) {
                            double discountAmount = (p.getPrice() * discount) / 100;
                            p.setPrice(p.getPrice() - discountAmount);
                            return 0; // product discounted
                        }
                    }
                    return 3;
                }
                return 2;
            }
        }
        return 1;
    }

    //ex3
    public int generateGiftCard(String userid,int value){


        for (User u : users) {
            if (u.getId().equals(userid)) {
                if (u.getBalance() >= value) {
                    u.setBalance(u.getBalance() - value);

                    String valueStr = String.valueOf(value)+"CE";
                    String randoms = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                    Random random = new Random();
                    StringBuilder randomToCombine = new StringBuilder();

                    for (int i = 0; i < 10; i++) {
                        int index = random.nextInt(randoms.length());
                        randomToCombine.append(randoms.charAt(index));
                    }

                    String code = valueStr + randomToCombine.toString();

                    u.getGeneratedGiftsCards().add(code);// to add it to the User available gift cards
                    giftsCardsGenerated.add(code);// to add it to the System available gift cards
                    return 0;
                }
                return 2;
            }
        }
        return 1;
    }

    //ex4
    public int redeemGiftCard(String userid,String code){
        for (User u : users) {
            if (u.getId().equals(userid)) {
                for (String c: giftsCardsGenerated){
                    if (c.equals(code)){
                        int index = code.indexOf("CE");
                        String cardValue = code.substring(0,index);
                        u.setBalance(u.getBalance()+Double.parseDouble(cardValue));
                        giftsCardsGenerated.remove(code);
                        u.getGeneratedGiftsCards().remove(code);
                        return 0;
                    }

                }
                return 2;
            }
        }
        return 1;
    }

    //ex5
    public int inviteRewards(String newuserid, String hostuserid){
        for (User hostUser : users) {
            if (hostUser.getId().equals(hostuserid)) {
                for(User newUser: users){
                    if (newUser.getId().equals(newuserid)){
                        hostUser.setBalance(hostUser.getBalance()+25);
                        newUser.setBalance(newUser.getBalance()+25);
                        return 0;
                    }
                }
                return 2;
            }
        }
        return 1;
    }

    public void addtoWishlist(String userid,String productid) {}
    public void buyforOthersUserWishlists(String userid,String productid) {}
    public int fivetimesforsamemerchanttogetdiscount(String userid,String productid) {return 25;}
    public void productattimerecnetdiscount(String userid,String productid) {}


}
