package com.example.yevgen.myapplication;

import android.database.sqlite.SQLiteDatabase;

import com.example.zlv_skripchenko.BuildConfig;
import com.example.zlv_skripchenko.db.Controller;
import com.example.zlv_skripchenko.db.DatabaseOpenHelper;
import com.example.zlv_skripchenko.utils.TestItem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.Java6Assertions.assertThat;


/**
 * Created by Yevgen on 14.06.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 25)
public class DatabaseOpenHelperTest {

    private String name = "Steve";
    private String surname = "Jobs";
    private String email = "Save@abc.com";
    private String contact = "67890";

    private DatabaseOpenHelper mInstance;

    @Before
    public void setUp() throws Exception {
        mInstance = DatabaseOpenHelper.getDatabaseOpenHelperInstance(RuntimeEnvironment.application);
    }

    @After
    public void tearDown() {
        mInstance.close();
    }

    @Test
    public void testInitialGetReadableDatabase() throws Exception {
        mInstance = DatabaseOpenHelper.getDatabaseOpenHelperInstance(RuntimeEnvironment.application);
        SQLiteDatabase database = mInstance.getReadableDatabase();
        assertInitialDB(database, mInstance);
    }



    private static void assertInitialDB(SQLiteDatabase database, DatabaseOpenHelper helper) {
        assertDatabaseOpened(database, helper);
        assertThat(helper.onCreateCalled).isTrue();
    }

    private static void assertDatabaseOpened(SQLiteDatabase database, DatabaseOpenHelper helper) {
        assertThat(database).isNotNull();
        assertThat(database.isOpen()).isTrue();
        assertThat(helper.onOpenCalled).isTrue();
        assertThat(helper.onUpgradeCalled).isFalse();
    }





    @Test
    public void testSubsequentGetReadableDatabase() throws Exception {
        mInstance = DatabaseOpenHelper.getDatabaseOpenHelperInstance(RuntimeEnvironment.application);
        mInstance.getReadableDatabase();
        mInstance.close();
        SQLiteDatabase database = mInstance.getReadableDatabase();
        assertSubsequentDB(database, mInstance);
    }
    private static void assertSubsequentDB(SQLiteDatabase database, DatabaseOpenHelper mInstance) {
        assertDatabaseOpened(database, mInstance);
        assertThat(mInstance.onCreateCalled).isFalse();
    }
    @Test
    public void testSameDBInstanceSubsequentGetReadableDatabase() throws Exception {
        SQLiteDatabase db1 = mInstance.getReadableDatabase();
        SQLiteDatabase db2 = mInstance.getReadableDatabase();

        assertThat(db1).isSameAs(db2);
    }
    @Test
    public void testInitialGetWritableDatabase() throws Exception {
        SQLiteDatabase database = mInstance.getWritableDatabase();
        assertInitialDB(database, mInstance);
    }
    @Test
    public void testSubsequentGetWritableDatabase() throws Exception {
        mInstance.getWritableDatabase();
        mInstance.close();
        assertSubsequentDB(mInstance.getWritableDatabase(), mInstance);
    }
    @Test
    public void testSameDBInstanceSubsequentGetWritableDatabase() throws Exception {
        SQLiteDatabase db1 = mInstance.getWritableDatabase();
        SQLiteDatabase db2 = mInstance.getWritableDatabase();
        assertThat(db1).isSameAs(db2);
    }
    @Test
    public void testClose() throws Exception {
        SQLiteDatabase database = mInstance.getWritableDatabase();

        assertThat(database.isOpen()).isTrue();
        mInstance.close();
        assertThat(database.isOpen()).isFalse();
    }






    @Test
    public void testSaveReadDatabase() throws Exception {
        boolean isSuccessful = Controller.write(name,surname, email, contact);
        assertTrue(isSuccessful);
        ArrayList<TestItem> items  = Controller.readTest(name,surname, email, contact);
        assertThat(items).isNotNull();
        assertTrue(items.size()>0);
        TestItem item = items.get(0);
        assertEquals(item.contact, contact);
        assertEquals(item.surname, surname);
        assertEquals(item.email, email);
        Controller.delete(item);
        ArrayList<TestItem> items1  = Controller.readTest(name,surname, email, contact);
        assertThat(items1).isNotNull();
    }


}
