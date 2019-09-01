import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
 * Create TransactionException, DigitalWallet, and DigitalWalletTransaction classes here.
 */
 /*public interface ErrorCodeCostant {

int CODE_400 = 400;
int CODE_402 = 402;
int CODE_403 = 403;

}
 public interface ErrorCodeCostant {

int USER_NOT_AUTHORIZED = 400;
int INVALID_AMOUNT  = 401;
int INSUFFICIENT_BALANCE;

}

public enum ExceptionCode implements ErrorCodeCostant {

USER_NOT_AUTHORIZED(CODE_400, “User not authorized”),
INVALID_AMOUNT(CODE_402, “Amount should be greater than zero”),
INSUFFICIENT_BALANCE(CODE_403, “Insufficient balance”);

private final int id;
private final String msg;

ExceptionCode(int id, String msg) {
this.id = id;
this.msg = msg;
}

public int getId() {
return this.id;
}

public String getMsg() {
return this.msg;
}
}*/
class TransactionException  extends Exception
{
    private String errMessage;
    private String errCode;

    TransactionException(String errMsg,String errCode)
    {
        this.errMessage = errMsg;
        this.errCode=errCode; 
    }
    public String getErrorCode()
    {
      return errCode;
    }
    public String getMessage()
    {
      return errMessage;
    }
}
class DigitalWallet
{
    private String walletId;
    private String userName;
    private String userAccessCode;
    int balance;

    DigitalWallet(String walletId, String userName)
    {
        this.walletId = walletId;
        this.userName = userName;
        this.userAccessCode = null;
    }
    DigitalWallet(String walletId, String userName,String userAccessCode)
    {
        this.walletId = walletId;
        this.userName = userName;
        this.userAccessCode = userAccessCode;
    }
    public String getWalletId()
    {
        return walletId;
    }
    public String getUsername()
    {
        return userName;
    }
    public String getUserAcessToken()
    {
        return userAccessCode;
    }
    public int getWalletBalance()
    {
        return balance;
    } 
    public void setWalletBalance(int balance)
    {
        this.balance= balance;

    } 

}

class DigitalWalletTransaction 
{
    public void addMoney(DigitalWallet wallet, int amount) throws TransactionException
    {
        if(wallet.getUserAcessToken() == null)
            throw new TransactionException("User not authorized","USER_NOT_AUTHORIZED");
            if (amount <= 0)
                throw new TransactionException( "Amount should be greater than zero","INVALID_AMOUNT");
                
        wallet.setWalletBalance(wallet.getWalletBalance()+amount);
    }

    public void payMoney(DigitalWallet wallet, int amount) throws TransactionException
    {
         if(wallet.getUserAcessToken() == null)
            throw new TransactionException( "User not authorized","USER_NOT_AUTHORIZED");
         
        if (amount <= 0)
            throw new TransactionException( "Amount should be greater than zero","INVALID_AMOUNT");
        if (wallet.getWalletBalance()- amount < 0)
            throw new TransactionException("Insufficient balance","INSUFFICIENT_BALANCE");

        wallet.setWalletBalance(wallet.getWalletBalance()- amount);
    }

}

public class Solution {
    private static final Scanner INPUT_READER = new Scanner(System.in);
    private static final DigitalWalletTransaction DIGITAL_WALLET_TRANSACTION = new DigitalWalletTransaction();
    
    private static final Map<String, DigitalWallet> DIGITAL_WALLETS = new HashMap<>();
    
    public static void main(String[] args) {
        int numberOfWallets = Integer.parseInt(INPUT_READER.nextLine());
        while (numberOfWallets-- > 0) {
            String[] wallet = INPUT_READER.nextLine().split(" ");
            DigitalWallet digitalWallet;
            
            if (wallet.length == 2) {
                digitalWallet = new DigitalWallet(wallet[0], wallet[1]);
            } else {
                digitalWallet = new DigitalWallet(wallet[0], wallet[1], wallet[2]);
            }
            
            DIGITAL_WALLETS.put(wallet[0], digitalWallet);
        }
        
        int numberOfTransactions = Integer.parseInt(INPUT_READER.nextLine());
        while (numberOfTransactions-- > 0) {
            String[] transaction = INPUT_READER.nextLine().split(" ");
            DigitalWallet digitalWallet = DIGITAL_WALLETS.get(transaction[0]);
            
            if (transaction[1].equals("add")) {
                try {
                    DIGITAL_WALLET_TRANSACTION.addMoney(digitalWallet, Integer.parseInt(transaction[2]));
                    System.out.println("Wallet successfully credited.");
                } catch (TransactionException ex) {
                    System.out.println(ex.getErrorCode() + ": " + ex.getMessage() + ".");
                }
            } else {
                try {
                    DIGITAL_WALLET_TRANSACTION.payMoney(digitalWallet, Integer.parseInt(transaction[2]));
                    System.out.println("Wallet successfully debited.");
                } catch (TransactionException ex) {
                    System.out.println(ex.getErrorCode() + ": " + ex.getMessage() + ".");
                }
            }
        }
        
        System.out.println();
        
        DIGITAL_WALLETS.keySet()
                .stream()
                .sorted()
                .map((digitalWalletId) -> DIGITAL_WALLETS.get(digitalWalletId))
                .forEachOrdered((digitalWallet) -> {
                    System.out.println(digitalWallet.getWalletId()
                            + " " + digitalWallet.getUsername()
                            + " " + digitalWallet.getWalletBalance());
                });
    }
}