//STUDENT-EDITABLE-BEGIN
catan.client = (function Client_NameSpace(){

	var ViewIniter = (function ViewIniter_Object(){
	
		var mapRadius = 3;
		var Views = {
			Resources:catan.resources.View,
			TurnTracker:catan.turntracker.View,
			VP:catan.points.View,
			Map:catan.map.View,
			MapOverlay:catan.views.overlays.MapOverlay,
			Log:catan.comm.View.LogView,
		}
		Controllers = 	{
			Resources:catan.resources.Controller,	
			TurnTracker:catan.turntracker.Controller,
			VP:catan.points.Controller,
			Map:catan.map.Controller,
			Log:catan.comm.Controller.LogController,
			Setup:catan.setup.Controller,
		}
		
		function ViewIniter(){};
		
		ViewIniter.resourceView = function  initResourceView(views,controllers, model){
			var map = controllers.map;
			var buildMoves = {};
			
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
			
		ViewIniter.vpTrackerView = function  initVPTrackerView(views,controllers, model){
			var view = new Views.VP();
			var controller = new Controllers.VP(view, undefined, model);
			
			controllers.vp = controller;
			views.vp = view;
		} 
		
		ViewIniter.logView = function  initLogView(views,controllers, clientModel){
			var logPane = document.getElementById("log-pane");
			var params = {
				message: "No game history to display",
				parentElem:logPane,
				id:"log",
			}
			
			var logView = new Views.Log(params);
					
			var logController = new Controllers.Log(logView,clientModel);
					
			views.log = logView;
			controllers.log = logController;
		}
		
		ViewIniter.mapView = function  initMapView(views,controllers, model){
			var MAP_RADIUS = 4;
			var height = $(window).height() || window.innerHeight;   // returns height of browser viewport
			var width = $(window).width() || window.innerWidth;   // returns width of browser viewport
			
			var view = new Views.Map(height - 200,MAP_RADIUS*2-1,MAP_RADIUS*2-1);
			
			var overlayView = new Views.MapOverlay();
			overlayView.setCancelAllowed(false);
			
			var controller = new Controllers.Map(view,overlayView,model);
			view.setController(controller);
			overlayView.setController(controller);
			
			views.map = view;
			controllers.map = controller;
		}
		
		ViewIniter.initAll = function initAll(views,controllers,clientModel){
			var viewFunctionsInOrder = [
				this.turnTrackerView,
				this.resourceView,
				this.vpTrackerView,
				this.logView,
				this.mapView,
			]
			var _views = views;
			var _controllers = controllers;
			var _clientModel = clientModel;
            
            function initView(viewInitFunction){
				viewInitFunction(_views,_controllers,_clientModel);
			};
            
			viewFunctionsInOrder.map(initView);
            	
			controllers.setup = new Controllers.Setup(clientModel, controllers.map);
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
		
		
		CatanGame.prototype.domLoaded = function domLoaded(){
			this.getClientModel().initFromServer(
				core.makeAnonymousAction(this,this.makeViewsAndControllers,undefined));
		}	
		
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
                    // Add your Observer here
                }
            }())
		};
		
		CatanGame.getPlayable = function(proxy){
			var returnValue = new CatanGame(proxy);
			
			$(document).ready(function () {
				returnValue.domLoaded();
			});

			return returnValue;
		}

		return CatanGame;
		
	}());
	
	return {Client:CatanGame};
}());


