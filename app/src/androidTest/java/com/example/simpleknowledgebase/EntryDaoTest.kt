package com.example.simpleknowledgebase

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
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

    // Tests don't semm to work with id=0 of the Entry instance : Entry(0,date1,...)

    @Test
    fun insertEntry() = runTest {

    val entry = Entry(1,"date1","title1","description2","date1","source1")
        dao.insertEntry(entry)
        val allEntries = dao.findAll()
        assertThat(allEntries).contains(entry)
    }

    @Test
    fun checkAllEntries() = runTest {

        val entry = Entry(1,"dateT","titlet","descriptionT","dateT","sourceT")
        dao.insertEntry(entry)
        val allEntries = dao.findAll()
        assertThat(allEntries).doesNotContain(entry)
    }


    @Test
    fun findKeyword() = runTest{
        val entry = Entry(5,"date1","title2","c3","des4","src5")
        dao.insertEntry(entry)
        val keywordEntry = dao.findKeyword("title")
        //assertThat(entry).isInstanceOf(String::class.java)
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

        val entry = Entry(1,"date1","title1","description2","date1","source1")
        dao.insertEntry(entry)
        dao.deleteEntry(entry)
        val allEntries = dao.findAll()
        assertThat(allEntries).doesNotContain(entry)
    }



}

