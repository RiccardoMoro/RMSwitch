Changelog
------
#### New from v1.0: <br />
(16/08/2016) <br />
-Initial release <br />

#### New from v1.0.1: <br />
(17/08/2016) <br />
-Fixes <br />
-Updated README <br />

#### New from v1.1.0: <br />
(27/08/2016) <br />
-Fixes <br />
-Added the TristateSwitch, a switch with three possible states (left, middle, right) <br />
-Updated README <br />
-Added CHANGELOG <br />

#### New from v1.1.1: <br />
(04/09/2016) <br />
-Fix: Now the aspectRatio option works correctly <br />
-Code improvements  <br />
-Fix: tristate switch default dimensions <br />
-Added the "slim design" option to make the switch looks similar to the default android one <br />

#### New from v1.2.0: <br />
(10/11/2016) <br />
-** Breaking change: no more boolean for slim design, instead, now you can choose between styles with the RMAbstractSwitch.DESIGN_*** constants and the RMSwitch.setSwitchDesign(int switchDesign) method <br />
-** Breaking change: now RMSwitchObserver and RMTristateSwitchObserver returns the instance of the clicked RMSwitch/RMTristateSwitch with the current selection value <br />
-** Breaking change: now RMSwitch.getSwitchToggleCheckedDrawable(), RMTristateSwitch.getSwitchToggleLeftDrawable() etc returns a drawable and not a drawable resource int <br />
-Added the "DESIGN_ANDROID" switch design option, which resemble the default material switch <br />
-Added the possibility to set a Drawable as switch toggle image (you can still set a drawable resource with the old methods) <br />
-Improvement: Updated gradle and Android target version <br />
-Lot of code improvements <br />
-Updated CHANGELOG <br />
-Updated README <br />
