package com.example.yevgen.myapplication;

import android.os.Environment;

import com.example.zlv_skripchenko.BuildConfig;
import com.example.zlv_skripchenko.activities.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowEnvironment;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 25)
public class MainActivityTest {



    @Test
    public void shouldNotBeNull() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        assertNotNull(activity);
//        TextView tv = (TextView) activity.findViewById(R.id.id_tv_hello);
//        assertNotNull(tv);
//      //  assertTrue("TextView contains incorrect text","Hello world!".equals(tv.getText().toString()));
//        assertTrue("Hello".equals(tv.getText().toString()));

    }



    @Test
    public void testSaveToFile() {
        String filename = "test.txt";
        String expectedContents = "test contents";
        ShadowEnvironment.setExternalStorageState(Environment.MEDIA_MOUNTED);
      //  String absolutePath = MainActivity.saveToFile(filename, expectedContents);
//        File expectedFile = new File(absolutePath);
//        Assert.assertTrue(expectedFile.exists());
//        expectedFile.delete();
    }




    @Test(expected = IllegalStateException.class)
    public void testSaveToFile_mediaUnmounted() {
        String filename = "test.txt";
        String expectedContents = "test contents";
        ShadowEnvironment.setExternalStorageState(Environment.MEDIA_UNMOUNTED);
        ///MainActivity.saveToFile(filename, expectedContents);
    }




}