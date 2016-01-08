//STUDENT-EDITABLE-BEGIN
/**
    This is the namespace for the intitial game round
    @module catan.setup
    @namespace setup
*/

var catan = catan || {};
catan.setup= catan.setup || {};

catan.setup.Controller = (function(){
	
	var Controller = catan.core.BaseController;
    
	/** 
		@class SetupRoundController
		@constructor 
		@extends misc.BaseController
		@param {models.ClientModel} clientModel
		@param {map.MapController} mapController
	*/
	var SetupRoundController = (function (){
		
		var SetupRoundController = function (clientModel, mapController){
			this.mapController = mapController;
			
			Controller.call(this,undefined,clientModel);
		};
        
		core.forceClassInherit(SetupRoundController,Controller);
        
		return SetupRoundController;
	}());
    
	return SetupRoundController;
}());

