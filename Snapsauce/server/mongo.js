var mongoose = require('mongoose');

mongoose.connect("mongodb://vk117:1234@ds119820.mlab.com:19820/snapsauce");
var Schema = mongoose.Schema;

var user_details = new Schema({
    email: {type: String, required: true, unique: true},
    password: {type: String, required: true},
    name: {type: String, required: true},
    phone: {type: Number, required: true}
});

var product_details = new Schema({
    category: {type: String, required: true},
    price: {type: String, required: true},
    calories: {type: String, required: true},
    name: {type: String, required: true, unique: true},
    time: {type: String, required: true}
});

var cart_details = new Schema({
    orderBy: {type: String, required: true},
    name: {type: String, required: true},
    quantity: {type: String, required: true},
    unitPrice: {type: String, required: true},
    prepTime: {type: String, required:true}
});

var order_details = new Schema({
    orderBy: {type: String, required: true},
    prepTime: {type: String, required: true},
    status: {type: String, required: true},
    name: {type: String, required: true}
})


var Order = mongoose.model('Order', order_details);
var Cart = mongoose.model('Cart', cart_details);
var Product = mongoose.model('Product', product_details);
var User = mongoose.model('User', user_details);

exports.User = User;
exports.Product = Product;
exports.Cart = Cart;
exports.Order = Order;
exports.mongoose = mongoose;


