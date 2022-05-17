package com.company.GameStore.service;

import com.company.GameStore.DTO.*;

import com.company.GameStore.exception.QueryNotFoundException;
import com.company.GameStore.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceLayer {

    TshirtRepository tshirtRepository;
    GameRepository gameRepository;
    ConsoleRepository consoleRepository;
    InvoiceRepository invoiceRepository;
    SalesTaxRateRepository salesTaxRateRepository;

    @Autowired
    public ServiceLayer(GameRepository gameRepository, ConsoleRepository consoleRepository, TshirtRepository tshirtRepository,InvoiceRepository invoiceRepository, SalesTaxRateRepository salesTaxRateRepository) {

        this.gameRepository = gameRepository;
        this.consoleRepository = consoleRepository;
        this.tshirtRepository = tshirtRepository;
        this.invoiceRepository = invoiceRepository;
        this.salesTaxRateRepository = salesTaxRateRepository;
    }

    // CLEAR DATABASE
    public void clearDatabase() {
        gameRepository.deleteAll();
        tshirtRepository.deleteAll();
        consoleRepository.deleteAll();
        invoiceRepository.deleteAll();
    }

    //Jpa Searches
    public List<Tshirt> getTshirtByColor(String color){return tshirtRepository.findByColor(color);}
    public List<Tshirt> getTshirtBySize(String size){return tshirtRepository.findBySize(size);}
    public List<Tshirt> getTshirtByColorAndSize(String color, String size){return tshirtRepository.findByColorAndSize(color,size);}

    //TShirt CRUD
    public List<Tshirt> getAllTshirt(){return tshirtRepository.findAll();}
    public Optional<Tshirt> getSingleTshirt(int id) {
        Optional<Tshirt> tshirt = tshirtRepository.findById(id);
        return tshirt.isPresent()? Optional.of(tshirt.get()) : null;
    }
    public Tshirt addTshirt(Tshirt tshirt) {return tshirtRepository.save(tshirt);}

    public void updateTshirt(Tshirt tshirt) {tshirtRepository.save(tshirt);}

    public void deleteTshirt(int id) {tshirtRepository.deleteById(id);}

    // GAME CRUD OPERATIONS
    public List<Game> getAllGames() {return gameRepository.findAll();}

    public List<Game> getGamesByStudio(String studio) { return gameRepository.findByStudio(studio); }

    public List<Game> getGamesByEsrbRating(String esrbRating) { return gameRepository.findByEsrbRating(esrbRating); }

    public List<Game> getGamesByStudioAndEsrbRating(String studio, String esrbRating) { return gameRepository.findByStudioAndEsrbRating(studio, esrbRating); }

    public Optional<Game> getGameByTitle(String title) { return gameRepository.findByTitle(title); }

    public Optional<Game> getSingleGame(int id) { return gameRepository.findById(id); }

    public Game addGame(Game game) { return gameRepository.save(game); }

    public void updateGame(Game game) { gameRepository.save(game); }

    public void deleteGame(int id) { gameRepository.deleteById(id); }

    // CONSOLE CRUD OPERATIONS  
    public List<Console> getConsolesByManufacturer(String manufacturer) {
        return consoleRepository.findByManufacturer(manufacturer);
    }

    public List<Console> getAllConsoles(){
        return consoleRepository.findAll();
    }

    public Optional<Console> getSingleConsole(int id) {
        return consoleRepository.findById(id);
    }

    public Console addConsole(Console console) {
        return consoleRepository.save(console);
    }

    public void updateConsole(Console console) {
        consoleRepository.save(console);
    }

    public void deleteConsole(int id) {
        consoleRepository.deleteById(id);
    }



    // Invoice CRUD -- Do not need to update / delete

    public List<Invoice> getAllInvoices() { return invoiceRepository.findAll(); }

    public Optional<Invoice> getInvoiceById(int id) {
        if (invoiceRepository.findById(id).orElse(null) == null) {
            throw new QueryNotFoundException("An invoice with that ID does not exist yet.");
        }
        return invoiceRepository.findById(id);
    }


    public Invoice addInvoice(Invoice invoice) { return invoiceRepository.save(invoice); }
    @Transactional
    public void decreaseItemQuantity(Invoice invoice) {
        // You will have to use invoice.getItem_type to select your repository that you are going to use (switch case)

           int requestedAmount = invoice.getQuantity();

        switch (invoice.getItem_type()){
                case "game" :
                    Game game = getSingleGame(invoice.getItem_id()).get();
                    int availableAmount = game.getQuantity();
                    int updatedAmount = availableAmount - requestedAmount;
                    game.setQuantity(updatedAmount);
                    updateGame(game);
                    break;
                case "console":
                    Console console = getSingleConsole(invoice.getItem_id()).get();
                    int availableAmount = console.getQuantity();
                    int updatedAmount = availableAmount - requestedAmount;
                    console.setQuantity(updatedAmount);
                    updateConsole(console);
                    break;
                case "tshirt":
                    Tshirt tshirt = getSingleTshirt(invoice.getItem_id()).get();
                    int availableAmount = tshirt.getQuantity();
                    int updatedAmount = availableAmount - requestedAmount;
                    tshirt.setQuantity(updatedAmount);
                    updateTshirt(tshirt);
                    break;
                default:
//                    quantity = "expects a quantity";
            }


        // create a variable (requestedAmount) that stores the invoice quantity
        // create a variable (availableAmount) that store the current item quantity
        // to get available amount you need to use repository.findById(invoice.getItem_id)
        // create a variable (remainingAmount)
        // remainingAmount = avaiableAmount - requestedAmount
        // Save the remainingAmount on the item (Console/Game/Tshirt)
    }
}

<<<<<<< HEAD

=======
    public Invoice addInvoice(Invoice invoice) {
        Invoice updatedInvoice = invoice;
        updatedInvoice.setTax(applyTaxRate(invoice));

        return invoiceRepository.save(updatedInvoice);
    }

    public double applyTaxRate(Invoice invoice) {
        double priceBeforeTax = invoice.getQuantity() * invoice.getUnit_price();
        System.out.println(priceBeforeTax);

        SalesTaxRate salesTax = salesTaxRateRepository.findByState("TX");
        System.out.println(salesTax.getRate());



        System.out.println(salesTaxRateRepository.findByState("TX").getRate());
        double taxRate = salesTaxRateRepository.findByState(invoice.getState()).getRate();
        System.out.println(taxRate);
        return priceBeforeTax * taxRate;
    }

}
>>>>>>> 146300c33fecf062c832e9fefac9c19e293bb6da

