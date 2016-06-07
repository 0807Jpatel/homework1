package tdlm.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import tdlm.data.DataManager;
import tdlm.gui.Workspace;
import saf.AppTemplate;
import saf.controller.AppFileController;
import saf.ui.AppGUI;
import tdlm.data.ToDoItem;
import tdlm.gui.InputDialog;

/**
 * This class responds to interactions with todo list editing controls.
 * 
 * @author McKillaGorilla
 * @version 1.0
 */
public class ToDoListController {
    AppTemplate app;
    
    public ToDoListController(AppTemplate initApp) {
	app = initApp;
    }
    
    public void processAddItem() {	
	// ENABLE/DISABLE THE PROPER BUTTONS	
	Workspace workspace = (Workspace)app.getWorkspaceComponent();
	DataManager dataManager = (DataManager)app.getDataComponent();
	InputDialog idd = new InputDialog("", "", LocalDate.now(), LocalDate.now(), false, app);
	ToDoItem toDoItem= idd.getToDo();
	if(toDoItem != null){
	    dataManager.addItem(toDoItem);
	    workspace.enableButtons(true);
	}
	workspace.reloadWorkspace();
    }
    
    public void processRemoveItem() {
	Workspace workspace = (Workspace)app.getWorkspaceComponent();
	DataManager dataManager = (DataManager)app.getDataComponent();
	TableView<ToDoItem> itemsTable = workspace.getitemsTable();
	ToDoItem selectedItem = itemsTable.getSelectionModel().getSelectedItem();
	dataManager.removeItem(selectedItem);
	if(itemsTable.getItems().isEmpty())
	    workspace.enableButtons(false);
	app.getGUI().updateToolbarControls(false);
	workspace.reloadWorkspace();	
    }
    
    public void processMoveUpItem() {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
	DataManager dataManager = (DataManager)app.getDataComponent();
	TableView<ToDoItem> itemsTable = workspace.getitemsTable();
	ToDoItem selectedItem = itemsTable.getSelectionModel().getSelectedItem();
	dataManager.moveUp(selectedItem);
	app.getGUI().updateToolbarControls(false);
	workspace.reloadWorkspace();
    }
    
    public void processMoveDownItem() {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
	DataManager dataManager = (DataManager)app.getDataComponent();
	TableView<ToDoItem> itemsTable = workspace.getitemsTable();
	ToDoItem selectedItem = itemsTable.getSelectionModel().getSelectedItem();
	dataManager.moveDown(selectedItem);
	app.getGUI().updateToolbarControls(false);
	workspace.reloadWorkspace();
    }
    
    public void processEditItem() {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
	DataManager dataManager = (DataManager)app.getDataComponent();
	TableView<ToDoItem> itemsTable = workspace.getitemsTable();
	ToDoItem selectedItem = itemsTable.getSelectionModel().getSelectedItem();
	
	
	if(selectedItem != null){
	InputDialog idd = new InputDialog(selectedItem.getCategory(), selectedItem.getDescription(), 
		selectedItem.getStartDate(), selectedItem.getEndDate(), selectedItem.getCompleted(), app);
	ToDoItem toDoItem = idd.getToDo();
	if(toDoItem != null)
	    dataManager.replace(selectedItem, toDoItem);
	workspace.reloadWorkspace();
	}
    }
}
