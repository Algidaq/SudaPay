# SudaPay :-
#### is an android library that allows you to add payment process to your application
very easy to implement and execute.
This library is provided by SudaColon devs to provid you and your e-commerce customers with the most **safest and secure**
payment gateway .

#### Follow insturction blew :-

1. Add **maven repository** to your root.gradle file in android
````gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

````
2. Add **SudaPay dependency**
````gradle
dependencies {
	        implementation 'com.github.Algidaq:SudaPay:0.3.4'
	}
````

3. **Sync Project**

4.In your **XML layout file add SudaPay Button**
````xml
<com.example.sudapay.LogInButton
        android:id="@+id/paymentButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        />
````
5. In your **Strings File add SPID {sudaPay Id provided by our webSite}**
for example
`````xml
<string name="SPID">123*******</string>
`````
6.In your **mainfest file add SudaPay activity**
````xml
<activity android:name="com.example.sudapay.SudaPayActivity"/>
````
7. in your **java class** start an intent and add extras and set **Call back Interface** to the same Java class for getting response back
   **Amount You want to transfere most be postive integer**
   **Account Number you want to Transfere to.**
for example:-
````java

 paymentButton.setOnClickListener((e)->{
            
                Intent in2 = new Intent(this,SudaPayActivity.class);
                in2.putExtra("amount",amount);
                in2.putExtra("accountNumber",accountNumber);
                SudaPayActivity.callBack = this;
                startActivity(in2);
            
        });
    }

//Payment CallBack method that gives You 1 if operation seceeded -1 if it faild 
    @Override
    public void onOperationCompleted(int Query) {

                
             
             }
````


   
