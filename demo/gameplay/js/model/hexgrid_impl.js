//STUDENT-EDITABLE-BEGIN   
var catan = catan || {};
catan.models = catan.models || {};

/**
	This module contains the map
	
	@module		catan.models
	@namespace models
*/

catan.models.Map = (function mapNameSpace(){
    
    var hexgrid = catan.models.hexgrid;
    
    var Map = (function Map_Class(){
       
		function Map(radius)
		{
			this.hexGrid = hexgrid.HexGrid.getRegular(radius, CatanHex);
		}
		return Map;
		
    }());
    
    /**
	This class represents an edge. It inherits from BaseContainer.
    The data in this class (that you get from the JSON model) is independent of the hexgrid, except for the location.
    Therefore, we leave it up to you to decide how to implement it.
    It must however implement one function that the hexgrid looks for: 'isOccupied' - look at its documentation.
    From the JSON, this object will have two properties: location, and ownerID.
    Besides the 'isOccupied' method, you may add any other methods that you need.
    
    @constructor
    @extends hexgrid.BaseContainer
	
	@class CatanEdge
	*/
    var CatanEdge = (function CatanEdge_Class(){
    
        core.forceClassInherit(CatanEdge, hexgrid.BaseContainer);
    
        function CatanEdge(){
			//jQuery("#debug").append("<br>In Edge Constructor");
		}
        
        return CatanEdge;
    }());
	
	CatanEdge.prototype.isOccupied = function() 
	{
		return false; //default implementation. Change this!
	}
    
    /**
	This class represents a vertex. It inherits from BaseContainer.
    The data in this class (that you get from the JSON model) is independent of the hexgrid, except for the location.
    Therefore, we leave it up to you to decide how to implement it.
    It must however implement one function that the hexgrid looks for: 'isOccupied' - look at its documentation.
    From the JSON, this object will have three properties: location, ownerID and worth.
    Besides the 'isOccupied' method, you may add any other methods that you need.
    
    @constructor
    @extends hexgrid.BaseContainer
	
	@class CatanVertex
	*/
    var CatanVertex = (function CatanVertex_Class(){
    
        core.forceClassInherit(CatanVertex, hexgrid.BaseContainer);
        
        function CatanVertex(){
			//jQuery("#debug").append("<br>In Vertex Constructor");
		}
        
         return CatanVertex;
    }()); 
	
	CatanVertex.prototype.isOccupied = function() 
	{
		return false; //default implementation. Change this!
	}
    
    
    /**
	This class represents a Hex. You may add any methods that you need (e.g., to get the resource/hex type, etc.)
    
    In order to work with the hexgrid, this class must extend hexgrid.BasicHex (already done in the code). You also need to implement
    a CatanVertex and CatanEdge classes (stubs are provided in this file).  Look at their documentation to see what needs to be done there.
     
    The hexgrid will be passed an instance of this class to use as a model, and will pull the constructor from that instance. 
    (The core.forceInherit sets the constructor, in case you are curious how that works)
      
    @constructor
    @param {hexgrid.HexLocation} location - the location of this hex. It's used to generate locations for the vertexes and edges.
    @extends hexgrid.BasicHex
	
	@class CatanHex
	*/
    var CatanHex = (function CatanHex_Class(){
    
        core.forceClassInherit(CatanHex, hexgrid.BasicHex);
        
        function CatanHex(location){          
            hexgrid.BasicHex.call(this,location,CatanEdge,CatanVertex);
			$("#debug").append("<br>In CatanHex Constructor (" + this.getLocation().getX() + ", " + this.getLocation().getY() + ")");
        } 
        
        return CatanHex;
    }());
    
	return Map;

}());



