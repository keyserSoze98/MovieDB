package com.keysersoze.moviedb.data.repository

import com.keysersoze.moviedb.data.local.FavoriteMovieDao
import com.keysersoze.moviedb.data.local.FavoriteMovieEntity
import com.keysersoze.moviedb.domain.model.Movie
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesRepositoryTest {

    private lateinit var dao: FavoriteMovieDao
    private lateinit var repository: FavoritesRepositoryImpl

    @Before
    fun setup() {
        dao = mockk(relaxed = true)
        repository = FavoritesRepositoryImpl(dao)
    }

    @Test
    fun `addToFavorites inserts entity into dao`() = runTest {
        val movie = Movie(
            imdbId = "tt123",
            title = "Batman",
            year = "2023",
            poster = "poster"
        )

        repository.addToFavorites(movie)

        coVerify {
            dao.insert(
                FavoriteMovieEntity(
                    imdbId = "tt123",
                    title = "Batman",
                    year = "2023",
                    poster = "poster"
                )
            )
        }
    }

    @Test
    fun `removeFromFavorites deletes entity from dao`() = runTest {
        repository.removeFromFavorites("tt123")

        coVerify {
            dao.delete("tt123")
        }
    }

    @Test
    fun `getFavorites emits mapped domain movies`() = runTest {
        val entities = listOf(
            FavoriteMovieEntity(
                imdbId = "tt123",
                title = "Batman",
                year = "2023",
                poster = "poster"
            )
        )

        every { dao.getAllFavorites() } returns flowOf(entities)

        val result = repository.getFavorites().first()

        assertEquals(1, result.size)
        assertEquals("Batman", result[0].title)
        assertEquals("tt123", result[0].imdbId)
    }
}