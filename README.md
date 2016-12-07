# StockPrediction
Use of machine learning algorithms to predict a stock price based on news articles (to extract the words I use boilerpipe).

I am using [git-flow](https://github.com/nvie/gitflow) model. The most stable sources are available in master branch, while the latest ones are in development branch.

App uses naive bayes classifier and Yahoo API to predict whether a stock is going to rise or fall in the following few hours. I've also designed SQL database to save words from articles (and num. of their occurences), so that the classifier can use past data to easily predict the future. This implementation is currently in the testing phase.

Stay tuned for the implementation of a neural network instead of the naive bayes classifier.

Feel free to make a pull request.

NOTICE: Code needs to be refactored, expect that to be done until 1/1/2017. 
