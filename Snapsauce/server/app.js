
var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var cors = require('cors');
var mongo = require('./mongo');
var conn = mongo.mongoose.connection;

var User = mongo.User;
var Product = mongo.Product;
var Cart = mongo.Cart;
var Order = mongo.Order;

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

app.post('/add', function(req, res){

   var email = req.body.email;
   var password = req.body.password;
   var name = req.body.name;
   var phone = req.body.phone;

   var new_user = new User({email: email, password: password, name: name, phone: phone});

   new_user.save(function(err, new_user){
       if(err){
           res.status(400).end();
           console.log(err);
       }
       else{
           res.status(200).end();
       }
   });
})

app.get('/check/:email&:password', function(req, res){

    var email = req.params.email;
    var password = req.params.password;


    User.find({email: email, password: password}, function(err, user, next){
        if(user.length){
            res.status(200).end();
        }
        else{
            res.status(400).end();
        }
    });
})

app.post('/addproduct', function(req, res){

    var category = req.body.category;
    var name = req.body.name;
    var price = req.body.price;
    var calories = req.body.calories;
    var time = req.body.time;

    var new_product = new Product({category: category, price: price, name: name, calories: calories, time: time});
    new_product.save(function(err, new_product){
        if(err){
            res.status(400).end();
        }
        else{
            res.status(200).end();
        }
    });
})

app.get('/getproducts/:category', function(req, res){

    var category = req.params.category;


        Product.find({category: category},function(err, result){
            if(err){
                res.status(400).end();
            }
            else if(result.length){
                res.status(200).send(result);
            }
            else{
                res.status(400).end();
            }
        });
    
})

app.delete('/deleteproduct/:name', function(req, res){

    var name = req.params.name;

    Product.remove({name: name}, function(err, result){
        if(err){
            res.status(400).end();
        }
        else{
            res.status(200).end();
        }
    });
})

app.post('/addtocart', function(req, res){

    var orderBy = req.body.orderBy;
    var name = req.body.name;
    var quantity = req.body.quantity;
    var unitPrice = req.body.unitPrice;
    var prepTime = req.body.prepTime;

    var add_cart = new Cart({orderBy: orderBy, name: name, quantity: quantity, unitPrice: unitPrice, prepTime: prepTime});
    add_cart.save(function(err, toCart){
        if(err){
            res.status(400).end();
        }
        else{
            res.status(200).end();
        }
    });
})

app.get('/getcart/:user', function(req, res){

    var user = req.params.user;

    Cart.find({orderBy: user}, function(err, result){
        if(err){
            res.status(400).end();
        }
        else if(result.length){
            res.status(200).send(result);
        }
        else{
            res.status(400).end();
        }
    })
})

app.delete('/deletecart/:name&:orderBy', function(req, res){

    var name = req.params.name;
    var orderBy = req.params.orderBy;

    console.log(name);
    console.log(orderBy);

    Cart.remove({name: name, orderBy: orderBy}, function(err, result){
        if(err){
            res.status(400).end();
        }
        else{
            res.status(200).end();
        }
    })
})


app.post('/addorder', function(req, res){

    var orderBy = req.body.orderBy;
    var prepTime = req.body.prepTime;
    var status = req.body.status;
    var name = req.body.name;
    
    var new_order = new Order({orderBy: orderBy, prepTime: prepTime, status: status, name: name});
    
    new_order.save(function(err, toOrders){
        if(err){
            res.status(400).end();
        }
        else{
            res.status(200).end();
        }
    });
})


app.delete('/deletecart/:user', function(req, res){

    var user = req.params.user;

    Cart.remove({orderBy: user}, function(err, result){
        if(err){
            res.status(400).end();
        }
        else{
            res.status(200).end();
        }
    })
})


app.get('/getorders', function(req, res){

    Order.find({}, function(err, result){
        if(err){
            res.status(400).end();
        }
        else if(result.length){
            res.status(200).send(result);
        }
        else{
            res.status(400).end();
        }
    })
})


app.post('/updatestatus', function(req, res){

    var user = req.body.user;

    Order.update({orderBy: user}, {$set:{status: "Ready for pickup"}}, function(err, result){
        if(err){
            res.status(400).end();
        }
        else{
            res.status(200).end();
        }
    });
})


app.get('/getorders/:user', function(req, res){

    var user = req.params.user;

    Order.find({orderBy: user}, function(err, result){
        if(err){
            res.status(400).end();
        }
        else if(result.length){
            res.status(200).send(result);
        }
        else{
            res.status(400).end();
        }
    })
})

app.delete('/deleteorder/:name&:user', function(req, res){

    var name = req.params.name;
    var user = req.params.name;

    console.log(name);
    console.log(user);

    Order.remove({orderBy: user, name: name}, function(err, result){
        if(err){
            res.status(400).end();
        }
        else{
            res.status(200).end();
        }
    })
})


app.listen(8080, function(){
    console.log("Server listening");
})


