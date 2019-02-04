# PolygonProgressView
Polygon progress for android


## build.gradle(project)
```
allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}	
```

## build.gradle(app)
```
  dependencies {
	        implementation 'com.github.amoda65:PolygonProgressView:1.0'
	}
```	
## Xml
```
<progress.gan.poly.com.lib.PolygonProgressView
        android:layout_width="120dp"
        android:layout_height="120dp"
        app:ppv_fill_color="#55c46d"
        app:ppv_max="200"
        app:ppv_num_sides="8"
        app:ppv_progress_value="50"
        app:ppv_stroke_color="#55c46d"
        app:ppv_stroke_width="8" />
```

## Code

```
polygonProgressView.setProgressValue(i);
polygonProgressView.setNumberOfSides(i);
polygonProgressView.setStrokeWidth(i);
polygonProgressView.setProgressColor(Color.parseColor("#000"));
polygonProgressView.setStrokeColor(Color.parseColor("#000"));
```
