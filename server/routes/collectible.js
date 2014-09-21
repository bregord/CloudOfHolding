var mongoose = require('mongoose')
  , collectible = require('../models/collectible.js');

module.exports = CollectibleList;

function CollectibleList(connection) {
  mongoose.connect(connection);
}

CollectibleList.prototype = {
  showCollectibles: function(req, res) {
    collectible.find({equipped: true}, function foundTasks(err, collectibles) {
      res.render('index',{title: 'My Collectibles', collectibles: collectibles})
    });
  },

  addCollectible: function(req,res) {
    var collectible = req.body.collectible;
    newCollectible = new collectible();
    newCollectible.itemName = collectible.name || '';
    newCollectible.urlHigh = collectible.urlHigh || '';
    newCollectible.urlPebble = collectible.urlPebble || '';
    newCollectible.timePickedUp = collectible.timePickedUp || (new Date());
    newCollectible.timeDropped = collectible.timeDropped || (new Date());
    newCollectible.description = collectible.description || '';
    newCollectible.equipped = collectible.equipped || false;
    newCollectible.save(function savedCollectible(err){
      if(err) {
        throw err;
      }
    });
    res.redirect('/');
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
    res.redirect('/');
  }
}
