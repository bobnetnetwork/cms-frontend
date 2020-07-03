package network.bobnet.cms.filestorage

import org.junit.Before
import org.junit.Test

import org.mockito.MockitoAnnotations.initMocks
import org.assertj.core.api.Assertions.assertThat
import org.springframework.web.multipart.MultipartFile
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import java.nio.charset.StandardCharsets

class ExtendedFileTest {

    private lateinit var file: MultipartFile

    private lateinit var underTest: ExtendedFile

    @Before
    fun setup(){
        initMocks(this)
        file = MockMultipartFile("file", "test contract.pdf", MediaType.APPLICATION_PDF_VALUE, "<<pdf data>>".toByteArray(StandardCharsets.UTF_8))
        underTest = ExtendedFile(file)
    }

    @Test
    fun checkSlug(){
        assertThat(underTest.slug).isEqualTo("test-contract")
    }

    @Test
    fun checkFullName(){
        assertThat(underTest.fullName).isEqualTo("test-contract.pdf")
    }
}