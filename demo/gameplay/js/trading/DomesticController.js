//STUDENT-EDITABLE-BEGIN
/**
    This is the namespace for domestic trading
    @module catan.trade
    @submodule catan.trade.domestic
    @namespace domestic
*/

var catan = catan || {};
catan.trade = catan.trade ||{};
catan.trade.domestic = catan.trade.domestic ||{};

catan.trade.domestic.Controller= (function trade_namespace(){

	var Controller = catan.core.BaseController;
	var Definitions = catan.definitions;
	var ResourceTypes = Definitions.ResourceTypes;
    
	var DomesticController = ( function DomesticController_Class() {
    
		/** 
		@class DomesticController
		@constructor 
		@extends misc.BaseController
		@param {domestic.View} view
		@param {misc.WaitOverlay} waitingView
		@param {domestic.AcceptView} acceptView
		@param {models.ClientModel} clientModel
		*/
		function DomesticController(view,waitingView,acceptView,clientModel){
			Controller.call(this,view,clientModel);
			this.waitingView = waitingView;
			this.acceptView = acceptView;
		};
        
		DomesticController.prototype = core.inherit(Controller.prototype);
         
         
		/******** Methods called by the Domestic View *********/
        
        /**
        * @method setResourceToSend
        * @param{String} resource the resource to send ("wood","brick","sheep","wheat","ore")
        * @return void
        */
		DomesticController.prototype.setResourceToSend = function(resource){
		};
        
		/**
		 * @method setResourceToReceive
		 * @param{String} resource the resource to receive ("wood","brick","sheep","wheat","ore")
		 * @return void
		 */
		 DomesticController.prototype.setResourceToReceive = function(resource){
		};
        
		/**
		  * @method unsetResource
		  * @param{String} resource the resource to clear ("wood","brick","sheep","wheat","ore")
		  * @return void
		  */
		DomesticController.prototype.unsetResource = function(resource){
		};
        
		/**
		 * @method setPlayerToTradeWith
		 * @param{int} playerNumber the player to trade with
		 * @return void
		 */
		DomesticController.prototype.setPlayerToTradeWith = function(playerNumber){
		};
        
		/**
		* Increases the amount to send or receive of a resource
		* @method increaseResourceAmount
		* @param{String} resource ("wood","brick","sheep","wheat","ore")
		* @return void
		*/
		DomesticController.prototype.increaseResourceAmount = function(resource){
		};
        
		/**
		 * Decreases the amount to send or receive of a resource
		 * @method decreaseResourceAmount
		 * @param{String} resource ("wood","brick","sheep","wheat","ore")
		 * @return void
		 */
		DomesticController.prototype.decreaseResourceAmount = function(resource){
		};
        
		/**
		  * Sends the trade offer to the accepting player
		  * @method sendTradeOffer
		  * @return void
		  */
		DomesticController.prototype.sendTradeOffer = function(){
		};
        
        
		/******************* Methods called by the Accept Overlay *************/
		 
        /**
        * Finalizes the trade between players
        * @method acceptTrade
        * @param{Boolean} willAccept
        * @return void
		*/
		DomesticController.prototype.acceptTrade = function(willAccept){
		};
            
		return DomesticController;
    }());
			
	return DomesticController;
}());


