# StockPrediction
Use of machine learning algorithms to predict stock price based on article news (to extract words i use boilerpipe)

I am using [git-flow](https://github.com/nvie/gitflow) model. The most stable sources are available in master branch, while the latest ones are in development branch.

I am using naive bayes classifier and Yahoo API to predict whether is stock going to rise or fall in the following few hours. I've also designed SQL database to save words from articles (and num. of their occurences), so that classifier can use past data to easily predict future. This implementation is currently in testing phase.

App uses simple GUI:
![alt tag](http://oi66.tinypic.com/vvzfn.jpg)

Stay tuned for implementation of a neural network instead of the naive bayes classifier.

Feel free to make pull request.
