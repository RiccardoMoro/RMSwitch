RMSwitch
======

<img src="switch-sample.gif" title="sample" />

A simple View that works like a switch, but with more customizations. 
With the option to choose between two or three states. (from V1.1)

[Changelog] (CHANGELOG.md)

Download
------
####Gradle:
```groovy
compile 'com.rm:rmswitch:1.1.0'
```

## Usage

To use them, just add this to your layout file

```xml
    <!-- Two states switch -->
    <com.rm.rmswitch.RMSwitch
                android:id="@+id/your_id"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content" />
                
    <!-- Three states switch -->                
    <com.rm.rmswitch.RMTristateSwitch
                        android:id="@+id/your_id2"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content" />
```

... if you need to use this View's custom xml attributes (shown in a table below) do not forget to add this to your root layout
```
xmlns:app="http://schemas.android.com/apk/res-auto"
```


To see how it looks in the preview screen of Android Studio, build your project first

And this in your Activity
```java
public class MainActivity extends AppCompatActivity {
    RMSwitch mSwitch;
    RMTristateSwitch mTristateSwitch;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mSwitch = (RMSwitch) findViewById(R.id.your_id);
        mTristateSwitch = (RMTristateSwitch) findViewById(R.id.your_id2);
        
        // Add a Switch state observer
        mSwitch.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(boolean isChecked) {
                                Toast.makeText(MainActivity.this, 
                                        "Switch state: " + 
                                        (isChecked ? "checked" : "not checked"), Toast.LENGTH_LONG)
                                        .show();
            }
        });
        
        mTristateSwitch.addSwitchObserver(new RMTristateSwitch.RMTristateSwitchObserver() {
            @Override
            public void onCheckStateChange(@RMTristateSwitch.State int state) {
                Toast
                        .makeText(MainActivity.this,
                                state == RMTristateSwitch.STATE_LEFT ?
                                        "Left" :
                                        state == RMTristateSwitch.STATE_MIDDLE ?
                                                "Middle" :
                                                "Right",
                                Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```

####Supported Attributes
RMSwitch
------
| XML Attribute                 | Java method                                                     	| Description                                                                                                     	| Default value                                      	            |
|-------------------------	    |-----------------------------------------------------------------	|-----------------------------------------------------------------------------------------------------------------	|----------------------------------------------------	            |
| checked                  	    | setChecked(boolean checked)                                     	| The initial state of the Switch, if checked or not                                                              	| false                                              	            |
| enabled                  	    | setEnabled(boolean enabled)                                     	| If not enabled, the Switch will not be clickable, but it is still possible to change its state programmatically 	| true                                               	            |
| forceAspectRatio         	    | setForceAspectRatio(boolean forceAspectRatio)                   	| Force the Switch aspect ratio                                                                                   	| true                                               	            |
| switchBkgCheckedColor    	    | setSwitchBkgCheckedColor(@ColorInt int color)                   	| The background color of the Switch if checked                                                                   	| your current theme colorControlHighlight attribute 	            |
| switchBkgNotCheckedColor 	    | setSwitchBkgNotCheckedColor(@ColorInt int color)                	| The background color of the Switch if not checked                                                               	| the same as switchBkgCheckedColor                	                |
| switchToggleCheckedColor      | setSwitchToggleCheckedColor(@ColorInt int color)                	| The color of the Switch toggle if checked                                                                       	| your current theme colorAccent attribute           	            |
| switchToggleNotCheckedColor   | setSwitchToggleNotCheckedColor(@ColorInt int color)             	| The color of the Switch toggle if not checked                                                                   	| white                                              	            |
| switchToggleCheckedImage      | setSwitchToggleCheckedDrawableRes(@DrawableRes int drawable)    	| The image to be shown on the toggle if checked                                                                  	| the same as switchToggleNotCheckedImage if set, none otherwise    |
| switchToggleNotCheckedImage   | setSwitchToggleNotCheckedDrawableRes(@DrawableRes int drawable) 	| The image to be shown on the toggle if not checked                                                              	| the same as switchToggleCheckedImage if set, none otherwise       |

RMTristateSwitch
------
| XML Attribute                 | Java method                                                     	| Description                                                                                                     	| Default value                                      	            |
|-------------------------	    |-----------------------------------------------------------------	|-----------------------------------------------------------------------------------------------------------------	|----------------------------------------------------	            |
| state                  	    | setState(@State int state)                                     	| The initial state of the Switch, if left, middle or right                                                         | left                                              	            |
| enabled                  	    | setEnabled(boolean enabled)                                     	| If not enabled, the Switch will not be clickable, but it is still possible to change its state programmatically 	| true                                               	            |
| forceAspectRatio         	    | setForceAspectRatio(boolean forceAspectRatio)                   	| Force the Switch aspect ratio                                                                                   	| true                                               	            |
| right_to_left                 | setRightToLeft(boolean rightToLeft)                               | The direction of the switch at every tap, if from left to right or right to left                                  | false
| switchBkgLeftColor    	    | setSwitchBkgLeftColor(@ColorInt int color)                   	    | The background color of the Switch if in the left state                                                           | your current theme colorControlHighlight attribute 	            |
| switchBkgMiddleColor    	    | setSwitchBkgMiddleColor(@ColorInt int color)                   	| The background color of the Switch if in the middle state                                                         | the same as switchBkgLeftColor                      	            |
| switchBkgRightColor    	    | setSwitchBkgRightColor(@ColorInt int color)                   	| The background color of the Switch if in the right state                                                     	    | the same as switchBkgLeftColor                     	            |
| switchToggleLeftColor    	    | setSwitchToggleLeftColor(@ColorInt int color)                   	| The background color of the Switch if in the left state                                                           | white                                              	            |
| switchToggleMiddleColor    	| setSwitchToggleMiddleColor(@ColorInt int color)                   | The background color of the Switch if in the middle state                                                         | your current theme primaryColor attribute          	            |
| switchToggleRightColor    	| setSwitchToggleRightColor(@ColorInt int color)                   	| The background color of the Switch Toggle if in the right state                                                   | your current theme accentColor attribute          	            |
| switchToggleLeftImage    	    | setSwitchToggleLeftDrawableRes(@ColorInt int color)               | The toggle image of the Switch if in the left state                                                               | the same as the one of the other states toggle image if at least one set, none otherwise 	    |
| switchToggleMiddleImage    	| setSwitchToggleMiddleDrawableRes(@ColorInt int color)             | The toggle image of the Switch if in the middle state                                                             | the same as the one of the other states toggle image if at least one set, none otherwise 	    |
| switchToggleRightImage    	| setSwitchToggleRightDrawableRes(@ColorInt int color)              | The toggle image of the Switch if in the right state                                                              | the same as the one of the other states toggle image if at least one set, none otherwise 	    |

The changes between the Switch states will be automatically cross-faded, to obtain a smooth experience

License
--------

    Copyright 2016 Riccardo Moro.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and 
    limitations under the License.
