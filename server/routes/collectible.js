var mongoose = require('mongoose');
var collectible = require('../models/collectible.js');

module.exports = CollectibleList;

function CollectibleList(connection) {
  mongoose.connect(connection);
}

CollectibleList.prototype = {
  showCollectibles: function(req, res) {
    collectible.find({equipped: true}, function foundTasks(err, collectibles) {
      res.json(collectibles);
    });
  },

  addCollectible: function(req,res) {
    var tempCollectible = req.body.collectible;
    var newCollectible = new collectible();
    newCollectible.itemName = tempCollectible.name || '';
    newCollectible.urlHigh = tempCollectible.urlHigh || '';
    newCollectible.urlPebble = tempCollectible.urlPebble || '';
    newCollectible.timePickedUp = tempCollectible.timePickedUp || (new Date());
    newCollectible.timeDropped = tempCollectible.timeDropped || (new Date());
    newCollectible.description = tempCollectible.description || '';
    newCollectible.equipped = tempCollectible.equipped || false;
    newCollectible.save(function savedCollectible(err){
      if(err) {
        throw err;
      }
    });
  },

  toggleCollectibleEquipped: function(req,res) {
    var collectibleId = req.body._id;
    var conditions = { _id: collectibleId };
    var updates = { equipped: !req.body.equipped };
    collectible.find(conditions, function updatedCollectible(err, collec) {
      if(err) {
        throw err;
      }
      collec.update( {equipped: !this.equipped} );
    });
  }
}
