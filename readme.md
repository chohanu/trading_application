
<h1 id="introduction">Introduction</h1>
<p>This application is an http based online trading simulation REST API that allows a user to create an account and use it to deposit and withdraw money from it.Money from the account is also used to buy and sell stocks from the IEX stock exchange.</p>
<p>It fetches real-time data through http requests to IEX cloud and uses CRUD operations to store it into a PostgreSQL database.</p>
<p>SpringBoot is used to launch and configure the application while Swagger is used to test all the end points</p>
<h1 id="quickstart">QuickStart</h1>
<p>Prerequisites:</p>
<ul>
<li>IEX Token</li>
<li>Docker</li>
<li>PostgreSQL Database<br>

</ul>
<p>Usage:</p>
<ul>
<li>
<p>Get IEX token from  <a href="https://iexcloud.io/">https://iexcloud.io</a></p>
</li>
<li>
<p>Start docker<br>
sudo systemctl start docker</p>
</li>
<li>
<p>Clone git repository<br>
git clone <a href="https://github.com/chohanu/trading_application.git">https://github.com/chohanu/trading_application.git</a></p>
</li>
<li>
<p>Start SpringBoot application and connect to the Database using the  shell script.<br>
bash start_up.sh PSQL_HOST PSQL_USER PSQL_PASSWORD</p>
</li>
<li>
  <br> </br>
<p>Use the API through Swagger UI<br>
<a href="http://localhost:8080/swagger-ui.html">http://localhost:8080/swagger-ui.html</a></p>
  <img src="/asset/swagger.PNG">
  
  <br> </br>
  
  
  
  
</li>
<li>
<p>We can also use the API though POSTMAN by executing the http  request and importing the API specification.<br>
<a href="http://localhost:8080/v2/api-docs">http://localhost:8080/v2/api-docs</a></p>
  <img src="/asset/postman.PNG">
  
  
  
  
</li>
</ul>
<h2 id="rest-api-usage">Rest API Usage</h2>
<p>Swagger is an open-source software framework that helps developers design, build, document, and consume REST APIs. The API structure is prone to errors and swagger makes the job of developers a lot easier by testing the endpoints with the help of interactive GUI.<br>

<p><strong>Quote Controller</strong></p>
<p>Through this controller,Iex  data quotes are retrieved by sending http requests to the IEXcloud and stored into the database using JDBC components.</p>
<ul>
<li>
<p>GET ‘/quote/dailyList’ - List all quotes securities in the database.</p>
</li>
<li>
<p>GET '/quote/iex/ticker/{ticker} – Displays the market data for the<br>
given ticket</p>
</li>
<li>
<p>POST '/quote/ticker/{ticker} - Adds a new ticker to the Quote table<br>
in the  database</p>
</li>
<li>
<p>PUT '/quote/ - Inserts quote data inside the database.</p>
</li>
<li>
<p>PUT ‘/quote/iexMarketData’ - Updates all the quotes stored inside the<br>
database.</p>
</li>
</ul>
<p><strong>Trader Controller</strong></p>
<p>This controller is used to withdraw or deposit money  from a trader’s account.</p>
<ul>
<li>DELETE ‘/trader/traderId/{traderId}’ - Deletes trader account if there is no money and no security on the account.</li>
<li>POST '/trader/ - Creates a trader and associated account</li>
<li>PUT '/trader/deposit/traderId/{traderId}/amount/{amount} – Deposits money/amount in the trader’s account balance</li>
<li>PUT '/trader/withdraw/traderId/{traderId}/amount/{amount} – Withdraws money/ amount from trader’s account balance<br>

</ul>
<p><strong>Order Controller</strong></p>
<p>This controller is used to buy or sell a stock for a trader.</p>
<ul>
<li>POST ‘/order/marketOrder’ – Stock can be bought if account balance and input size is not zero (positive value).Stock can be sold if size input is negative and account balance has enough securities.</li>
</ul>
<p><strong>App Controller</strong></p>
<ul>
<li><code>GET '/health'</code> Checks if SpringBoot app is running.</li>
</ul>



<h2 id="architecture">Architecture</h2>
<img src="/asset/archdgrm.PNG">




<p><strong>Controller</strong></p>
<p>This layer interacts with the user with the help of Swagger UI. It gets input from the user and then calls the corresponding service method</p>
<p><strong>Service</strong></p>
<p>This layer is known as the business logic. It encodes real world business rules that describe how the data needs to be created ,stored and altered. It interacts with the DAO layer.</p>
<p><strong>Dao</strong></p>
<p>Data Access Object-This layer is responsible for the CRUD operations  on the database.</p>
<p><strong>SpringBoot</strong></p>
<p>This is a framework that manages all of our application components with Apache Tomcat.</p>
<p><strong>Apache Tomcat</strong></p>
<p>It maps Http request to the appropriate methods.</p>
<p><strong>PSQL</strong></p>
<p>It is a free and open source relational database system. It collects information from Dao’s and stores them into the datadabse using JDBC components.</p>
<p><strong>IEX</strong><br>
It provides access to Canadian and US Stocke market data.This application uses IEX cloud to collect most updated market data by sending http request</p>


