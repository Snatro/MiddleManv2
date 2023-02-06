package entities;


import com.example.middle_man.AddPersonController;
import com.example.middle_man.AddStoreController;
import com.example.middle_man.AddSupplyController;

public sealed interface EntityCreation permits AddStoreController, AddPersonController, AddSupplyController {
    void addEntityToList();
}
