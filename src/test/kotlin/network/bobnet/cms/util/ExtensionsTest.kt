package network.bobnet.cms.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations.initMocks


class ExtensionsTest {

    private lateinit var underTest: Extensions

    @Before
    fun setup(){
        initMocks(this)
        underTest = Extensions()
    }

    @Test
    fun checkSlugify(){
        val rawString = "[Example String]"
        val slug = "example-string"

        assertThat(underTest.slugify(rawString)).isEqualTo(slug)
    }

    @Test
    fun checkSlugifyCode(){
        val rawString = "[Example String]"
        val slug = "example_string"

        assertThat(underTest.slugifyCode(rawString)).isEqualTo(slug)
    }
}
