var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var CollectibleSchema = new Schema({
    itemName      : String
  , urlHigh       : String
  , urlPebble     : String
  , timePickedUp  : { type: Date, default: Date.now }
  , timeDropped   : { type: Date, default: Date.now }
  , description   : String
  , equipped      : Boolean
});

module.exports = mongoose.model('CollectibleModel', CollectibleSchema);