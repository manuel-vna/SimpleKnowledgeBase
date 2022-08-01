package com.example.simpleknowledgebase

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class EntryDaoTest {

    private lateinit var database: EntryDatabase
    private lateinit var dao: EntryDao

    @Before
    fun setup() {

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            EntryDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.entryDao()
    }

    @After
    fun teardown(){
        database.close()
    }

// Tests do not seem to work with id=0 of the Entry instance: Entry(0,date1,...), Reason unknown.

    @Test
    fun insertEntry() = runTest {

        val entry = Entry(5,"date1","title1","description2","date1","source1")
        dao.insertEntry(entry)
        val allEntries = dao.findAll()

        assertThat(allEntries).contains(entry)

    }

    @Test
    fun findKeyword() = runTest{
        val entry = Entry(5,"date1","title2","c3","des4","src5")
        dao.insertEntry(entry)
        val keywordEntry = dao.findKeyword("title")

        assertThat(keywordEntry.first().category).isEqualTo("c3")
    }


    @Test
    fun updateEntry() = runTest {

        val entry = Entry(5,"date1","title1","cat1","description1","source1")
        dao.insertEntry(entry)
        //id is the same because the aim is to overwrite the same db-entry with new values
        val entryUpdate = Entry(5,"date2","title2","cat2","description2","source2")
        dao.updateEntry(entryUpdate)
        val updatedEntry = dao.findKeyword("title")

        assertThat(updatedEntry.first().title).isEqualTo("title2")
    }

    @Test
    fun deleteEntry() = runTest {

        val entry = Entry(5,"date1","title1","description2","date1","source1")
        dao.insertEntry(entry)
        dao.deleteEntry(entry)
        val allEntries = dao.findAll()

        assertThat(allEntries).doesNotContain(entry)
    }

    @Test
    fun getAllCategories() = runTest {

        val entry1 = Entry(5,"date1","title1","description1","date1","source1")
        dao.insertEntry(entry1)
        val entry2 = Entry(6,"date2","title2","description2","date2","source2")
        dao.insertEntry(entry2)
        var allCategories = dao.findAllCategories()

        assertThat(allCategories).contains("description1")

    }

    @Test
    fun getSingeleCategory() = runTest {

        val entry1 = Entry(5,"date1","title1","category1","desc1","source1")
        dao.insertEntry(entry1)
        val entry2 = Entry(6,"date2","title2","category2","desc2","source2")
        dao.insertEntry(entry2)
        var category = dao.findCategory("category1")

        assertThat(category.first().category).isEqualTo("category1")

    }

    @Test
    fun getTotalRowNumber() = runTest {

        val entry1 = Entry(5,"date1","title1","category1","desc1","source1") // row 1
        dao.insertEntry(entry1)
        val entry2 = Entry(6,"date2","title2","category2","desc2","source2") // row 2
        dao.insertEntry(entry2)
        val entry3 = Entry(7,"date3","title3","category3","desc3","source3") // row 3
        dao.insertEntry(entry3)
        val entry4 = Entry(8,"date4","title4","category4","desc4","source4") // row 3
        dao.insertEntry(entry4)
        //var rowNumber = dao.getTotalRowNumber()

        //assertThat(rowNumber).isEqualTo(4)

    }



}
