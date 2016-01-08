// STUDENT-EDITABLE-BEGIN
/**
    This is the namespace to hold the base classes
    @module catan.misc
    @namespace misc
*/

var catan = catan || {};
catan.core = catan.core || {};

catan.core.BaseController = (function baseControllerClass(){

	/** 
		This class serves as the basis for all controller classes.		
		This constructor should be called by all child classes.
		
		@class BaseController
		@constructor 
		@param view - The controller's view
		@param {models.ClientModel} clientModel - The controller's client model
	*/
	function BaseController(view,clientModel){
		this.setView(view);
		this.setClientModel(clientModel);
	};
	
	core.defineProperty(BaseController.prototype,"View");
	core.defineProperty(BaseController.prototype,"ClientModel");

	return BaseController;	
}());

