# DBpedia_chatbot_warmup
This repository contains the warm up task for the [Google Summer of Code 2017 DBpedia Chatbot](http://wiki.dbpedia.org/ideas/idea/282/first-chatbot-for-dbpedia/)

#Usage
The easiest way to run this is using Maven:
` mvn exec:java `

The bot understands several formulations to translate arabic numbers into roman numerals

#Example Questions
`>What is 3 in roman?`


>III

`>Translate 3 and 5`


>III


>IV

If the bot is unsure what it should do, it asks for feedback



`>Can you please translate 4 and 5 into roman?`

>Hmmm... I'm not sure if I got that correctly


>Do you want me to translate these numbers?



>4  5

#Basic Idea
This is a closed-domain retrieval based bot. This means there are several predefined responses and keywords the bot is looking for.
Just taking any user input and parsing the numbers from it would destroy the chatbot character of this task, which is why I thought
it would be nice to force the user to tell the bot that it should translate the numbers.
