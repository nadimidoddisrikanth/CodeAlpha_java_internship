package Task2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// ---------------- STOCK CLASS ----------------
class Stock {
    private String symbol;
    private String companyName;
    private double price;

    public Stock(String symbol, String companyName, double price) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void displayStock() {
        System.out.printf(
                "%-10s %-20s $%.2f%n",
                symbol,
                companyName,
                price
        );
    }
}


// ---------------- TRANSACTION CLASS ----------------
class Transaction {
    private String type;
    private String stockSymbol;
    private int quantity;
    private double price;
    private double total;
    private String dateTime;

    public Transaction(
            String type,
            String stockSymbol,
            int quantity,
            double price,
            String dateTime) {

        this.type = type;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
        this.total = quantity * price;
        this.dateTime = dateTime;
    }

    public void displayTransaction() {
        System.out.printf(
                "%-8s %-10s %-10d $%-10.2f $%-10.2f %s%n",
                type,
                stockSymbol,
                quantity,
                price,
                total,
                dateTime
        );
    }
}


// ---------------- PORTFOLIO CLASS ----------------
class Portfolio {

    // Stock symbol -> quantity owned
    private HashMap<String, Integer> holdings;

    // Stock symbol -> total amount invested
    private HashMap<String, Double> investment;

    public Portfolio() {
        holdings = new HashMap<>();
        investment = new HashMap<>();
    }

    // Add shares after buying
    public void buy(String symbol, int quantity, double price) {

        holdings.put(
                symbol,
                holdings.getOrDefault(symbol, 0) + quantity
        );

        investment.put(
                symbol,
                investment.getOrDefault(symbol, 0.0)
                        + quantity * price
        );
    }

    // Remove shares after selling
    public boolean sell(String symbol, int quantity, double price) {

        if (!holdings.containsKey(symbol)) {
            return false;
        }

        int currentQuantity = holdings.get(symbol);

        if (quantity > currentQuantity) {
            return false;
        }

        double averageCost =
                investment.get(symbol) / currentQuantity;

        double remainingInvestment =
                investment.get(symbol)
                        - (averageCost * quantity);

        int remainingQuantity =
                currentQuantity - quantity;

        if (remainingQuantity == 0) {
            holdings.remove(symbol);
            investment.remove(symbol);
        } else {
            holdings.put(symbol, remainingQuantity);
            investment.put(symbol, remainingInvestment);
        }

        return true;
    }

    public int getQuantity(String symbol) {
        return holdings.getOrDefault(symbol, 0);
    }

    public double getInvestment(String symbol) {
        return investment.getOrDefault(symbol, 0.0);
    }

    public void displayPortfolio(
            HashMap<String, Stock> market) {

        if (holdings.isEmpty()) {
            System.out.println("\nYour portfolio is empty.");
            return;
        }

        System.out.println("\n================ PORTFOLIO ================");

        System.out.printf(
                "%-10s %-10s %-15s %-15s%n",
                "Symbol",
                "Quantity",
                "Invested",
                "Current Value"
        );

        System.out.println("---------------------------------------------");

        double totalInvested = 0;
        double totalCurrentValue = 0;

        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {

            String symbol = entry.getKey();
            int quantity = entry.getValue();

            double invested = investment.get(symbol);

            Stock stock = market.get(symbol);

            double currentValue =
                    quantity * stock.getPrice();

            totalInvested += invested;
            totalCurrentValue += currentValue;

            System.out.printf(
                    "%-10s %-10d $%-14.2f $%-14.2f%n",
                    symbol,
                    quantity,
                    invested,
                    currentValue
            );
        }

        System.out.println("---------------------------------------------");

        double profitLoss =
                totalCurrentValue - totalInvested;

        System.out.printf(
                "Total Invested      : $%.2f%n",
                totalInvested
        );

        System.out.printf(
                "Current Value       : $%.2f%n",
                totalCurrentValue
        );

        System.out.printf(
                "Profit / Loss       : $%.2f%n",
                profitLoss
        );

        System.out.println("=============================================");
    }

    public double getTotalCurrentValue(
            HashMap<String, Stock> market) {

        double total = 0;

        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {

            String symbol = entry.getKey();
            int quantity = entry.getValue();

            Stock stock = market.get(symbol);

            total += quantity * stock.getPrice();
        }

        return total;
    }

    public double getTotalInvestment() {

        double total = 0;

        for (double value : investment.values()) {
            total += value;
        }

        return total;
    }
}


// ---------------- USER CLASS ----------------
class User {

    private String name;
    private double cash;
    private Portfolio portfolio;

    public User(String name, double initialCash) {
        this.name = name;
        this.cash = initialCash;
        this.portfolio = new Portfolio();
    }

    public String getName() {
        return name;
    }

    public double getCash() {
        return cash;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public boolean buyStock(
            Stock stock,
            int quantity) {

        double totalCost =
                stock.getPrice() * quantity;

        if (totalCost > cash) {
            return false;
        }

        cash -= totalCost;

        portfolio.buy(
                stock.getSymbol(),
                quantity,
                stock.getPrice()
        );

        return true;
    }

    public boolean sellStock(
            Stock stock,
            int quantity) {

        if (portfolio.getQuantity(stock.getSymbol())
                < quantity) {

            return false;
        }

        double totalAmount =
                stock.getPrice() * quantity;

        boolean sold =
                portfolio.sell(
                        stock.getSymbol(),
                        quantity,
                        stock.getPrice()
                );

        if (sold) {
            cash += totalAmount;
            return true;
        }

        return false;
    }

    public double getTotalAccountValue(
            HashMap<String, Stock> market) {

        return cash +
                portfolio.getTotalCurrentValue(market);
    }
}


// ---------------- MAIN CLASS ----------------
public class StockTradingPlatform {

    private static Scanner scanner = new Scanner(System.in);

    private static HashMap<String, Stock> market =
            new HashMap<>();

    private static ArrayList<Transaction> transactions =
            new ArrayList<>();

    private static User user;


    // Initialize market
    public static void initializeMarket() {

        market.put(
                "AAPL",
                new Stock("AAPL", "Apple Inc.", 180.00)
        );

        market.put(
                "GOOG",
                new Stock("GOOG", "Alphabet Inc.", 150.00)
        );

        market.put(
                "MSFT",
                new Stock("MSFT", "Microsoft", 420.00)
        );

        market.put(
                "TSLA",
                new Stock("TSLA", "Tesla Inc.", 250.00)
        );

        market.put(
                "AMZN",
                new Stock("AMZN", "Amazon", 180.00)
        );
    }


    // Display market
    public static void displayMarket() {

        System.out.println("\n================ MARKET DATA ================");

        System.out.printf(
                "%-10s %-20s %s%n",
                "Symbol",
                "Company",
                "Price"
        );

        System.out.println("---------------------------------------------");

        for (Stock stock : market.values()) {
            stock.displayStock();
        }

        System.out.println("=============================================");
    }


    // Buy stock
    public static void buyStock() {

        displayMarket();

        System.out.print("\nEnter stock symbol: ");
        String symbol = scanner.nextLine().toUpperCase();

        if (!market.containsKey(symbol)) {
            System.out.println("Stock not found.");
            return;
        }

        Stock stock = market.get(symbol);

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return;
        }

        double total =
                stock.getPrice() * quantity;

        System.out.printf(
                "Total cost: $%.2f%n",
                total
        );

        boolean success =
                user.buyStock(stock, quantity);

        if (success) {

            transactions.add(
                    new Transaction(
                            "BUY",
                            symbol,
                            quantity,
                            stock.getPrice(),
                            java.time.LocalDateTime.now()
                                    .toString()
                    )
            );

            System.out.println(
                    "Stock purchased successfully!"
            );

        } else {

            System.out.println(
                    "Insufficient balance."
            );
        }
    }


    // Sell stock
    public static void sellStock() {

        System.out.print("\nEnter stock symbol: ");
        String symbol = scanner.nextLine().toUpperCase();

        if (!market.containsKey(symbol)) {
            System.out.println("Stock not found.");
            return;
        }

        Stock stock = market.get(symbol);

        int owned =
                user.getPortfolio()
                        .getQuantity(symbol);

        if (owned == 0) {
            System.out.println(
                    "You do not own this stock."
            );
            return;
        }

        System.out.println(
                "You currently own: "
                        + owned
                        + " shares"
        );

        System.out.print("Enter quantity to sell: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        if (quantity <= 0) {
            System.out.println(
                    "Quantity must be greater than zero."
            );
            return;
        }

        if (quantity > owned) {
            System.out.println(
                    "You cannot sell more shares than you own."
            );
            return;
        }

        boolean success =
                user.sellStock(stock, quantity);

        if (success) {

            transactions.add(
                    new Transaction(
                            "SELL",
                            symbol,
                            quantity,
                            stock.getPrice(),
                            java.time.LocalDateTime.now()
                                    .toString()
                    )
            );

            System.out.println(
                    "Stock sold successfully!"
            );

        } else {

            System.out.println(
                    "Unable to sell stock."
            );
        }
    }


    // Update market price
    public static void updateStockPrice() {

        displayMarket();

        System.out.print(
                "\nEnter stock symbol to update: "
        );

        String symbol =
                scanner.nextLine().toUpperCase();

        if (!market.containsKey(symbol)) {
            System.out.println(
                    "Stock not found."
            );
            return;
        }

        Stock stock = market.get(symbol);

        System.out.printf(
                "Current price: $%.2f%n",
                stock.getPrice()
        );

        System.out.print(
                "Enter new price: $"
        );

        double newPrice =
                scanner.nextDouble();

        scanner.nextLine();

        if (newPrice <= 0) {
            System.out.println(
                    "Price must be greater than zero."
            );
            return;
        }

        stock.setPrice(newPrice);

        System.out.println(
                "Stock price updated successfully!"
        );
    }


    // Transaction history
    public static void displayTransactions() {

        if (transactions.isEmpty()) {

            System.out.println(
                    "\nNo transactions available."
            );

            return;
        }

        System.out.println(
                "\n================ TRANSACTION HISTORY ================"
        );

        System.out.printf(
                "%-8s %-10s %-10s %-11s %-11s %s%n",
                "Type",
                "Symbol",
                "Quantity",
                "Price",
                "Total",
                "Date/Time"
        );

        System.out.println(
                "-----------------------------------------------------"
        );

        for (Transaction transaction : transactions) {
            transaction.displayTransaction();
        }

        System.out.println(
                "====================================================="
        );
    }


    // Display account summary
    public static void displayAccountSummary() {

        double portfolioValue =
                user.getPortfolio()
                        .getTotalCurrentValue(market);

        double totalInvestment =
                user.getPortfolio()
                        .getTotalInvestment();

        double profitLoss =
                portfolioValue - totalInvestment;

        double totalAccountValue =
                user.getTotalAccountValue(market);

        System.out.println(
                "\n================ ACCOUNT SUMMARY ================"
        );

        System.out.printf(
                "User Name           : %s%n",
                user.getName()
        );

        System.out.printf(
                "Available Cash      : $%.2f%n",
                user.getCash()
        );

        System.out.printf(
                "Portfolio Value     : $%.2f%n",
                portfolioValue
        );

        System.out.printf(
                "Total Account Value : $%.2f%n",
                totalAccountValue
        );

        System.out.printf(
                "Profit / Loss       : $%.2f%n",
                profitLoss
        );

        System.out.println(
                "================================================="
        );
    }


    // Main menu
    public static void main(String[] args) {

        initializeMarket();

        System.out.println(
                "=============================================="
        );

        System.out.println(
                "       STOCK TRADING PLATFORM"
        );

        System.out.println(
                "=============================================="
        );

        System.out.print(
                "Enter your name: "
        );

        String name =
                scanner.nextLine();

        // Starting virtual money
        user = new User(name, 10000.00);

        System.out.println(
                "\nWelcome, " + name + "!"
        );

        System.out.println(
                "Virtual starting balance: $10,000.00"
        );

        int choice;

        do {

            System.out.println(
                    "\n============== MAIN MENU =============="
            );

            System.out.println(
                    "1. Display Market Data"
            );

            System.out.println(
                    "2. Buy Stock"
            );

            System.out.println(
                    "3. Sell Stock"
            );

            System.out.println(
                    "4. View Portfolio"
            );

            System.out.println(
                    "5. View Transaction History"
            );

            System.out.println(
                    "6. Update Stock Price"
            );

            System.out.println(
                    "7. Account Summary"
            );

            System.out.println(
                    "8. Exit"
            );

            System.out.println(
                    "========================================"
            );

            System.out.print(
                    "Enter your choice: "
            );

            choice =
                    scanner.nextInt();

            scanner.nextLine();

            switch (choice) {

                case 1:
                    displayMarket();
                    break;

                case 2:
                    buyStock();
                    break;

                case 3:
                    sellStock();
                    break;

                case 4:
                    user.getPortfolio()
                            .displayPortfolio(market);
                    break;

                case 5:
                    displayTransactions();
                    break;

                case 6:
                    updateStockPrice();
                    break;

                case 7:
                    displayAccountSummary();
                    break;

                case 8:
                    System.out.println(
                            "\nThank you for using the Stock Trading Platform!"
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }

        } while (choice != 8);

        scanner.close();
    }
}