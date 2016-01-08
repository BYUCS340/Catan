//STUDENT-EDITABLE-BEGIN
catan.client = (function Client_NameSpace(){

	var ViewIniter = (function ViewIniter_Object(){
		var mapRadius = 3;
		var Views = {
			Roll:catan.roll.View,
			RollResult:catan.roll.ResultOverlay,
			Discard:catan.discard.View,
			Waiting:catan.misc.WaitOverlay,
			Resources:catan.resources.View,
			Log:catan.comm.View.LogView,
			Chat: catan.comm.View.ChatView,
			TurnTracker:catan.turntracker.View,
			VP:catan.points.View,
			EndGame:catan.misc.GameFinishedView,
			MarTrade:catan.trade.maritime.View,
			DomTrade:catan.trade.domestic.View,
			AcceptTrade:catan.trade.domestic.AcceptView,
			UseDevCard:catan.devCards.View,
			BuyDevCard:catan.devCards.BuyView,
			Map:catan.map.View,
			MapOverlay:catan.views.overlays.MapOverlay,
			Robber:catan.views.overlays.RobOverlay
		}
		Controllers = 	{
			Roll:catan.roll.Controller,
			Discard:catan.discard.Controller,
			Resources:catan.resources.Controller,
			Log:catan.comm.Controller.LogController,
			Chat:catan.comm.Controller.ChatController,
			TurnTracker:catan.turntracker.Controller,
			VP:catan.points.Controller,
			MarTrade:catan.trade.maritime.Controller,
			DomTrade:catan.trade.domestic.Controller,
			DevCard:catan.devCards.Controller,
			Map:catan.map.Controller
		}
		
		
		function ViewIniter(){};
		
		ViewIniter.rollView = function initRollView(views,controllers,model){
			var rollView = new Views.Roll();
			var rollResultView = new Views.RollResult();
			
			var rollController = new Controllers.Roll(rollView,rollResultView, model);
			
			rollView.setController(rollController);
			rollResultView.setController(rollController);
			
			views.roll = rollView;
			controllers.roll = rollController;		
		}

		ViewIniter.resourceView = function  initResourceView(views,controllers, model){
						
			var map = controllers.map;
			var buildMoves = {
				"Roads": core.makeAnonymousAction(map,map.startMove,["Road",false]),
				"Cities": core.makeAnonymousAction(map,map.startMove,["City",false]),
				"Settlements": core.makeAnonymousAction(map,map.startMove,["Settlement",false]),
				"DevCards": core.makeAnonymousAction(views.useDevCard,views.useDevCard.showModal,[true]),
				"BuyCard": core.makeAnonymousAction(views.buyDevCard,views.buyDevCard.showModal,[true])
			};
			
			var view = new Views.Resources();
			var controller = new Controllers.Resources(view,model,buildMoves);
			view.setController(controller);
			
			views.resources = view;
			controllers.resources = controller;
		}
		
		ViewIniter.turnTrackerView = function initTurnTrackerView (views,controllers, model){
			
			var view = new Views.TurnTracker();
			var controller = new Controllers.TurnTracker(view,model);
			view.setController(controller);
			
			views.turnTracker = view;
			controllers.turnTracker = controller;
		};
			
		ViewIniter.vpView = function  initVPView(views,controllers, model){
			var view = new Views.VP();
			var endGame = new Views.EndGame();
			var controller = new Controllers.VP(view, endGame, model);
			
			controllers.vp = controller;
			views.vp = view;
		} 
		
		ViewIniter.mapView = function  initMapView(views,controllers, model){
			var MAP_RADIUS = 4;
			
			var height = $(window).height() || window.innerHeight;   // returns height of browser viewport
			var width = $(window).width() || window.innerWidth;   // returns width of browser viewport
			var view = new Views.Map(height - 200,MAP_RADIUS*2-1,MAP_RADIUS*2-1);
			var robberView = new Views.Robber();
			
			var overlayView = new Views.MapOverlay();
			overlayView.setCancelAllowed(true);
			
			var controller = new Controllers.Map(view,overlayView, model,robberView);
			view.setController(controller);
			overlayView.setController(controller);
			robberView.setController(controller);
			
			views.map = view;
			controllers.map = controller;
		}
		
		ViewIniter.domesticTradeView = function initTradeView(views,controllers, clientModel){	
			var domView = new Views.DomTrade();
			var waitingView = new Views.Waiting("Waiting for the Trade to Go Through");
			var acceptView = new Views.AcceptTrade();
			var domController = new Controllers.DomTrade(domView,waitingView,acceptView,clientModel);
			
			domView.setController(domController);
			acceptView.setController(domController);
			
			views.domTrade = domView;
			controllers.domTrade = domController;
		}
		
		ViewIniter.maritimeTradeView = function initTradeView(views,controllers, clientModel){	
			var marView = new Views.MarTrade();
			var marController = new Controllers.MarTrade(marView, clientModel);
			marView.setController(marController);
			
			views.marTrade = marView;
			controllers.marTrade = marController;
		}
		
		ViewIniter.logView = function  initLogView      (views,controllers, clientModel){
			var logView = new Views.Log();
					
			var logController = new Controllers.Log(logView,clientModel);
					
			views.log = logView;
			controllers.log = logController;
		}
		
		ViewIniter.chatView = function initChatView (views,controllers, clientModel){
			var chatView = new Views.Chat();
			
			var chatController = new Controllers.Chat(chatView,clientModel);
			chatView.setController(chatController);
			
			views.chat = chatView;
			controllers.chat = chatController;
		}
		
		ViewIniter.devCards = function initDevCards (views,controllers, clientModel){
			views.useDevCard = new Views.UseDevCard();
			views.buyDevCard = new Views.BuyDevCard();
			
			var sAction = function(_this){
				mapC.doSoldierAction();
			}
			var rAction = function(_this){
				mapC.startDoubleRoadBuilding();
			}

			controllers.devCard  = new Controllers.DevCard(views.useDevCard, views.buyDevCard, clientModel, sAction, rAction);
			var mapC = controllers.map;
			views.buyDevCard.setController(controllers.devCard);
			views.useDevCard.setController(controllers.devCard);
		}
		ViewIniter.discard = function initDiscard (views,controllers, clientModel){
			var discardView = new Views.Discard();
			var waitingView = new Views.Waiting("Waiting for other Players to Discard");
			
			var discardController = new Controllers.Discard(discardView,waitingView, clientModel);
			discardView.setController(discardController);
			
			views.discard = discardView;
			controllers.discard = discardController;
		}
				
		ViewIniter.initAll = function initAll(views,controllers,clientModel){
			var viewFunctionsInOrder = [
				this.turnTrackerView,
				this.mapView,
				this.rollView,
				this.domesticTradeView,
				this.maritimeTradeView,
				this.devCards,
				this.resourceView,
				this.logView,
				this.chatView,
				this.vpView,
				this.discard
			]
			
			var _views = views;
			var _controllers = controllers;
			var _clientModel = clientModel;
			viewFunctionsInOrder.map(function(viewInitFunction){
				viewInitFunction(_views,_controllers,_clientModel);
			});	
		}
		
		return ViewIniter;
	}());

	var CatanGame = ( function CatanGame_Class(){
		var ClientModel = catan.models.ClientModel;

		core.defineProperty(CatanGame.prototype, "ClientModel");
        
        function getClientIDFromCookie(){
            return JSON.parse(decodeURIComponent(Cookies.get("catan.user"))).playerID;
        }
        
		function CatanGame(){
			this.setClientModel(new ClientModel(getClientIDFromCookie()));
		}        
        
        // called when the dom is loaded
		CatanGame.prototype.domLoaded = function domLoaded(){
			this.getClientModel().initFromServer(
				core.makeAnonymousAction(this,this.makeViewsAndControllers,undefined));
		}	
		
        // adds all the controllers as listeners to the client model
		CatanGame.prototype.makeViewsAndControllers = function makeViewsAndControllers(){
			var views = {}
			var controllers = {}
			var model = this.getClientModel();
			ViewIniter.initAll(views,controllers,model);
			this.views = views;
			this.controllers = controllers;
			(function(){
                for( name in controllers){         

                    var controller = controllers[name];
                    // implement adding listeners here
                }
            }())
		};
		
        
		CatanGame.getPlayable = function(){
			var returnValue = new CatanGame();
			
			$(document).ready(function () {
				returnValue.domLoaded();
			});
			return returnValue;
		}

		return CatanGame;
		
	}());
	
	return {Client:CatanGame};
}());


