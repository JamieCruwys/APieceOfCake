package uk.co.jamiecruwys.apieceofcake.main

import org.junit.Before
import org.junit.Test
import uk.co.jamiecruwys.apieceofcake.api.Cake

class MainPresenterTest {

    lateinit var tester: MainPresenterTester
    private var isSwipeToRefresh = false

    @Before
    fun setup() {
        tester = MainPresenterTester()
        isSwipeToRefresh = false
    }

    @Test
    fun `given response is null when app resumes then empty screen is shown`() {
        // Given
        tester.setSuccessResponse(null)

        // When
        tester.loadData(isSwipeToRefresh)

        // Then
        tester.verifyEmpty(isSwipeToRefresh)
    }

    @Test
    fun `given response is empty list when app resumes then empty screen is shown`() {
        // Given
        tester.setSuccessResponse(emptyList())

        // When
        tester.loadData(isSwipeToRefresh)

        // Then
        tester.verifyEmpty(isSwipeToRefresh)
    }

    @Test
    fun `given response is a list with one null element when app resumes then empty screen is shown`() {
        // Given
        tester.setSuccessResponse(listOf(null))

        // When
        tester.loadData(isSwipeToRefresh)

        // Then
        tester.verifyEmpty(isSwipeToRefresh)
    }

    @Test
    fun `given response is a list of many null elements when app resumes then empty screen is shown`() {
        // Given
        tester.setSuccessResponse(listOf(null, null, null))

        // When
        tester.loadData(isSwipeToRefresh)

        // Then
        tester.verifyEmpty(isSwipeToRefresh)
    }

    @Test
    fun `given response is one valid cake when app resumes then one cake is shown`() {
        // Given
        tester.setSuccessResponse(listOf(LEMON_CHEESECAKE_RAW))

        // When
        tester.loadData(isSwipeToRefresh)

        // Then
        tester.verifySuccess(isSwipeToRefresh, listOf(LEMON_CHEESECAKE))
    }

    @Test
    fun `given response is one valid cake when app resumes then one cake is shown and is capitalised`() {
        // Given
        tester.setSuccessResponse(listOf(VICTORIA_SPONGE_RAW))

        // When
        tester.loadData(isSwipeToRefresh)

        // Then
        tester.verifySuccess(isSwipeToRefresh, listOf(VICTORIA_SPONGE))
    }

    @Test
    fun `given response is many valid cakes when app resumes then cakes are shown in alphabetical order`() {
        // Given
        tester.setSuccessResponse(listOf(LEMON_CHEESECAKE_RAW, VICTORIA_SPONGE_RAW, BANANA_CAKE_RAW, CARROT_CAKE_RAW, BIRTHDAY_CAKE_RAW))

        // When
        tester.loadData(isSwipeToRefresh)

        // Then
        tester.verifySuccess(isSwipeToRefresh, listOf(BANANA_CAKE, BIRTHDAY_CAKE, CARROT_CAKE, LEMON_CHEESECAKE, VICTORIA_SPONGE))
    }

    @Test
    fun `given response contains duplicate cakes when app resumes then duplicates are removed`() {
        // Given
        tester.setSuccessResponse(listOf(LEMON_CHEESECAKE_RAW, BANANA_CAKE_RAW, LEMON_CHEESECAKE_RAW, null, BANANA_CAKE_RAW, VICTORIA_SPONGE_RAW, BIRTHDAY_CAKE_RAW, BIRTHDAY_CAKE_RAW))

        // When
        tester.loadData(isSwipeToRefresh)

        // Then
        tester.verifySuccess(isSwipeToRefresh, listOf(BANANA_CAKE, BIRTHDAY_CAKE, LEMON_CHEESECAKE, VICTORIA_SPONGE))
    }

    // With swipe to refresh enabled

    @Test
    fun `given response is null when swipe to refresh then empty screen is shown`() {
        // Given
        isSwipeToRefresh = true
        tester.setSuccessResponse(null)

        // When
        tester.loadData(isSwipeToRefresh)

        // Then
        tester.verifyEmpty(isSwipeToRefresh)
    }

    @Test
    fun `given response is empty list when swipe to refresh then empty screen is shown`() {
        // Given
        isSwipeToRefresh = true
        tester.setSuccessResponse(emptyList())

        // When
        tester.loadData(isSwipeToRefresh)

        // Then
        tester.verifyEmpty(isSwipeToRefresh)
    }

    @Test
    fun `given response is a list with one null element when swipe to refresh then empty screen is shown`() {
        // Given
        isSwipeToRefresh = true
        tester.setSuccessResponse(listOf(null))

        // When
        tester.loadData(isSwipeToRefresh)

        // Then
        tester.verifyEmpty(isSwipeToRefresh)
    }

    @Test
    fun `given response is a list of many null elements when swipe to refresh then empty screen is shown`() {
        // Given
        isSwipeToRefresh = true
        tester.setSuccessResponse(listOf(null, null, null))

        // When
        tester.loadData(isSwipeToRefresh)

        // Then
        tester.verifyEmpty(isSwipeToRefresh)
    }

    @Test
    fun `given response is one valid cake when swipe to refresh then one cake is shown`() {
        // Given
        isSwipeToRefresh = true
        tester.setSuccessResponse(listOf(LEMON_CHEESECAKE_RAW))

        // When
        tester.loadData(isSwipeToRefresh)

        // Then
        tester.verifySuccess(isSwipeToRefresh, listOf(LEMON_CHEESECAKE))
    }

    @Test
    fun `given response is one valid cake when swipe to refresh then one cake is shown and is capitalised`() {
        // Given
        isSwipeToRefresh = true
        tester.setSuccessResponse(listOf(VICTORIA_SPONGE_RAW))

        // When
        tester.loadData(isSwipeToRefresh)

        // Then
        tester.verifySuccess(isSwipeToRefresh, listOf(VICTORIA_SPONGE))
    }

    @Test
    fun `given response is many valid cakes when swipe to refresh then cakes are shown in alphabetical order`() {
        // Given
        isSwipeToRefresh = true
        tester.setSuccessResponse(listOf(LEMON_CHEESECAKE_RAW, VICTORIA_SPONGE_RAW, BANANA_CAKE_RAW, CARROT_CAKE_RAW, BIRTHDAY_CAKE_RAW))

        // When
        tester.loadData(isSwipeToRefresh)

        // Then
        tester.verifySuccess(isSwipeToRefresh, listOf(BANANA_CAKE, BIRTHDAY_CAKE, CARROT_CAKE, LEMON_CHEESECAKE, VICTORIA_SPONGE))
    }

    @Test
    fun `given response contains duplicate cakes when swipe to refresh then duplicates are removed`() {
        // Given
        isSwipeToRefresh = true
        tester.setSuccessResponse(listOf(LEMON_CHEESECAKE_RAW, BANANA_CAKE_RAW, LEMON_CHEESECAKE_RAW, null, BANANA_CAKE_RAW, VICTORIA_SPONGE_RAW, BIRTHDAY_CAKE_RAW, BIRTHDAY_CAKE_RAW))

        // When
        tester.loadData(isSwipeToRefresh)

        // Then
        tester.verifySuccess(isSwipeToRefresh, listOf(BANANA_CAKE, BIRTHDAY_CAKE, LEMON_CHEESECAKE, VICTORIA_SPONGE))
    }

    // Cake view

    @Test
    fun `given cakes shown when cake clicked then cake info dialog shown`() {
        // Given
        // Nothing to do here

        // When
        tester.clickCakeItem(LEMON_CHEESECAKE)

        // Then
        tester.verifyCakeItemClicked(LEMON_CHEESECAKE)
    }

    // Error states

    @Test
    fun `given network error when app resumes then network error screen is shown`() {
        // Given
        tester.setNetworkErrorResponse()

        // When
        tester.loadData(isSwipeToRefresh)

        // Then
        tester.verifyNetworkError(isSwipeToRefresh)
    }

    @Test
    fun `given network error when swipe to refresh then network error screen is shown`() {
        // Given
        isSwipeToRefresh = true
        tester.setNetworkErrorResponse()

        // When
        tester.loadData(isSwipeToRefresh)

        // Then
        tester.verifyNetworkError(isSwipeToRefresh)
    }

    @Test
    fun `given server error when app resumes then server error screen is shown`() {
        // Given
        tester.setServerErrorResponse(404)

        // When
        tester.loadData(isSwipeToRefresh)

        // Then
        tester.verifyServerError(isSwipeToRefresh)
    }

    @Test
    fun `given server error when swipe to refresh then server error screen is shown`() {
        // Given
        isSwipeToRefresh = true
        tester.setServerErrorResponse(404)

        // When
        tester.loadData(isSwipeToRefresh)

        // Then
        tester.verifyServerError(isSwipeToRefresh)
    }

    companion object {
        // Original items
        val LEMON_CHEESECAKE_RAW = Cake("Lemon cheesecake", "A cheesecake made of lemon", "https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg")
        val VICTORIA_SPONGE_RAW = Cake("victoria sponge", "sponge with jam", "https://upload.wikimedia.org/wikipedia/commons/0/05/111rfyh.jpg")
        val CARROT_CAKE_RAW = Cake("Carrot cake", "Bugs bunnys favourite", "https://hips.hearstapps.com/del.h-cdn.co/assets/18/08/1519321610-carrot-cake-vertical.jpg")
        val BANANA_CAKE_RAW = Cake("Banana cake", "Donkey kongs favourite", "http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg")
        val BIRTHDAY_CAKE_RAW = Cake("Birthday cake", "a yearly treat", "https://www.frenchvillagebakery.co.uk/databaseimages/prd_8594342__painted_pink_and_gold_cake_512x640.jpg")

        // Expected result after capitalisation
        val LEMON_CHEESECAKE = Cake("Lemon cheesecake", "A cheesecake made of lemon", "https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg")
        val VICTORIA_SPONGE = Cake("Victoria sponge", "Sponge with jam", "https://upload.wikimedia.org/wikipedia/commons/0/05/111rfyh.jpg")
        val CARROT_CAKE = Cake("Carrot cake", "Bugs bunnys favourite", "https://hips.hearstapps.com/del.h-cdn.co/assets/18/08/1519321610-carrot-cake-vertical.jpg")
        val BANANA_CAKE = Cake("Banana cake", "Donkey kongs favourite", "http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg")
        val BIRTHDAY_CAKE = Cake("Birthday cake", "A yearly treat", "https://www.frenchvillagebakery.co.uk/databaseimages/prd_8594342__painted_pink_and_gold_cake_512x640.jpg")
    }
}