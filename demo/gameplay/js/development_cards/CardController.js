//STUDENT-EDITABLE-BEGIN
/**
    This is the namespace for development cards
    @module catan.devCards
    @namespace devCards
*/

var catan = catan || {};
catan.devCards = catan.devCards || {};

catan.devCards.Controller = (function(){

	var Controller = catan.core.BaseController;
	var Definitions = catan.definitions;
	
	var DevCardController = (function card_namespace(){
		
		core.forceClassInherit(DevCardController,Controller);

		core.defineProperty(DevCardController.prototype, "BuyView");

		/**
		 * @class DevCardController
		 * @constructor
		 * @extends misc.BaseController
		 * @param {devCards.DevCardView} view
		 * @param {devCards.BuyCardView} buyView
		 * @param {models.ClientModel} clientModel
		 * @param {function} soldierAction
		 * @param {function} roadAction
		 */
		function DevCardController(view, buyView, clientModel, soldierAction, roadAction){
			Controller.call(this,view,clientModel);
			this.setBuyView(buyView);
		}
		
		/**
		 * Called when the player buys a development card
		 * @method buyCard
		 * @return void
		 */
		DevCardController.prototype.buyCard = function(){
		}
        
		/**
		 * Called when the player plays a year of plenty card
		 * @method useYearOfPlenty
		 * @param {String} resource1 The first resource to obtain
		 * @param {String} resource2 The second resource to obtain
		 * @return void
		 */
		DevCardController.prototype.useYearOfPlenty = function(resource1, resource2){
		}
        
		/**
		 * Called when the player plays a monopoly card
		 * @method useMonopoly
		 * @param {String} resource the resource to obtain
		 * @return void
		 */
		DevCardController.prototype.useMonopoly= function(resource){
		}
        
		/**
		 * Called when the player plays a monument card
		 * @method useMonument
		 * @return void
		 */
		DevCardController.prototype.useMonument = function(){
		}
        
		/**
		 * Called when the player plays a soldier card
		 * @method useSoldier
		 * @return void
		 */
		DevCardController.prototype.useSoldier= function(){
		}
        
		/**
		 * Called when the player plays the road building card
		 * @method useRoadBuild
		 * @return void
		 */
		DevCardController.prototype.useRoadBuild = function(resource){
		}

		return DevCardController;
	}());
	
	return DevCardController;
}());

