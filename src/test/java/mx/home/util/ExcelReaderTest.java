package mx.home.util;

import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import jxl.read.biff.BiffException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class ExcelReaderTest {

	@Test(expected = BiffException.class)
	// TODO? To transform in other kind of exception??? maybe a runtime???
	public void testLeerArchivoExcelFormatoNoSoportado() throws BiffException,
			IOException {
		new ExcelReader().leerArchivoExcel(readInputFile(new ClassPathResource(
				"circuits_johnson.pdf").getFile().getAbsolutePath()));
	}

	@Test()
	// TODO? To transform in other kind of exception??? maybe a runtime???
	public void testLeerArchivoExcelNoExiste() throws BiffException,
			IOException {
		new ExcelReader()
				.leerArchivoExcel(new ClassPathResource("noExisto.xyz")
						.getFile().getAbsolutePath());
	}

	@Test()
	public void testLeerArchivoExcel() throws BiffException, IOException {
		final ExcelReader excelReader = new ExcelReader();
		final List<List<String>> archivoLeido = excelReader
				.leerArchivoExcel(readInputFile(new ClassPathResource(
						"Dali Excel JXL.xls").getFile().getAbsolutePath()));
		final String cellsFromXslFile = getMessageLog(archivoLeido);
		LOG.debug("Read cells:\n" + cellsFromXslFile);
	}

	private String getMessageLog(final List<List<String>> archivoLeido) {
		final StringBuilder dataInXslFile = new StringBuilder();
		for (List<String> row : archivoLeido) {
			for (String cell : row) {
				dataInXslFile.append(cell);
				dataInXslFile.append("\n");
			}
		}
		return dataInXslFile.toString();
	}

	private InputStream readInputFile(final String fileName) {
		InputStream inputFile = null; // NOPMD by Cesar on 11/08/11 1:02
		try {
			// inputFile = new NDocumentInputStream(document);
			inputFile = new FileInputStream(fileName);
		} catch (IOException e) {
			final StringBuilder msgErr = new StringBuilder();
			msgErr.append("Error al leer el archivo de entrada.\n");
			msgErr.append("Verifique que el archivo ");
			msgErr.append(fileName);
			msgErr.append(" exista.");
			LOG.error("In readInputFile(). " + msgErr, e);
			assertNotNull(msgErr.toString(), inputFile);
		}
		return inputFile;
	}

	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(ExcelReaderTest.class);
	}

}
