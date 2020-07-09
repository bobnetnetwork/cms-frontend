package network.bobnet.cms.filestorage

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations.initMocks
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import java.nio.charset.StandardCharsets

class FileStorageImplTest {

    private lateinit var underTest: FileStorageImpl

    private lateinit var file1: MultipartFile
    private lateinit var file2: MultipartFile

    @Before
    fun setup(){
        initMocks(this)
        file1 = MockMultipartFile("file1", "test contract1.pdf", MediaType.APPLICATION_PDF_VALUE, "<<pdf data>>".toByteArray(StandardCharsets.UTF_8))
        file2 = MockMultipartFile("file2", "test contract2.pdf", MediaType.APPLICATION_PDF_VALUE, "<<pdf data>>".toByteArray(StandardCharsets.UTF_8))
        underTest = FileStorageImpl("tests")
    }

    @Test
    fun checkStore(){
        assertThat(underTest.store(file1)).isEqualTo("/filestorage/tests/test-contract1.pdf")

    }

    @Test
    fun checkLoadFile(){
        val resource = underTest.loadFile("test-contract1.pdf")
        val b = resource.exists() && resource.isReadable
        assertThat(b).isTrue()
    }

    @Test
    fun checkLoadFileIsNotExist(){
        val resource = underTest.loadFile("test-contract23.pdf")
        val b = resource.exists() && resource.isReadable
        assertThat(b).isFalse()
    }

    @Test
    fun checkDelete(){
        underTest.store(file2)
        underTest.delete("test-contract2.pdf")
        val resource = underTest.loadFile("test-contract2.pdf")
        val b = resource.exists() && resource.isReadable
        assertThat(b).isFalse()
    }

    @Test
    fun checkDeleteAll(){
        underTest.store(file2)
        underTest.deleteAll()
        val resource1 = underTest.loadFile("test-contract1.pdf")
        val resource2 = underTest.loadFile("test-contract2.pdf")
        val b = resource1.exists() && resource1.isReadable && resource2.exists() && resource2.isReadable
        assertThat(b).isFalse()
    }

}
