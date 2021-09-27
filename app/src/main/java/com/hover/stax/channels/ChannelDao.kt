package com.hover.stax.channels

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hover.stax.account.ChannelWithAccounts

@Dao
interface ChannelDao {

    @get:Query("SELECT * FROM channels WHERE published = 1 ORDER BY name ASC")
    val allInAlphaOrder: LiveData<List<Channel>>

    @Query("SELECT * FROM channels WHERE selected = :selected ORDER BY defaultAccount DESC, name ASC")
    fun getSelected(selected: Boolean): LiveData<List<Channel>>

    @Query("SELECT COUNT(*) FROM channels WHERE selected = :selected")
    fun getSelectedCount(selected: Boolean): Int

    @Query("SELECT * FROM channels WHERE id IN (:channel_ids) ORDER BY name ASC")
    fun getChannels(channel_ids: IntArray): LiveData<List<Channel>>

    @Query("SELECT * FROM channels WHERE country_alpha2 = :countryCode ORDER BY name ASC")
    fun getChannels(countryCode: String): List<Channel>

    @Query("SELECT * FROM channels WHERE country_alpha2 = :countryCode AND id IN (:channel_ids) ORDER BY name ASC")
    fun getChannels(countryCode: String, channel_ids: IntArray): LiveData<List<Channel>>

    @Query("SELECT * FROM channels WHERE id = :id LIMIT 1")
    fun getChannel(id: Int): Channel?

    @Query("SELECT * FROM channels WHERE id = :id LIMIT 1")
    fun getLiveChannel(id: Int): LiveData<Channel>

    @Transaction
    @Query("SELECT * FROM channels where selected = 1 ORDER BY name ASC")
    fun getChannelsAndAccounts(): List<ChannelWithAccounts>

    @get:Query("SELECT COUNT(id) FROM channels")
    val dataCount: Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg channels: Channel?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(channel: Channel?)

    @Update
    fun update(channel: Channel?)

    @Update
    fun updateAll(channel: List<Channel?>?)

    @Delete
    fun delete(channel: Channel?)

    @Query("DELETE FROM channels")
    fun deleteAll()
}