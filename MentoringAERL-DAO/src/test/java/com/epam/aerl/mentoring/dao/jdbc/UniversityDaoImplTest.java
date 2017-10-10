package com.epam.aerl.mentoring.dao.jdbc;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import com.epam.aerl.mentoring.entity.UniversityDTO;
import com.epam.aerl.mentoring.entity.UniversityStatusDTO;
import com.epam.aerl.mentoring.exception.DaoLayerException;
import com.epam.aerl.mentoring.type.UniversityStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@PrepareForTest(UniversityDaoImpl.class)
@ContextConfiguration("/dao-spring-test-context.xml")
public class UniversityDaoImplTest {

  @Rule
  public PowerMockRule rule = new PowerMockRule();

  @Mock
  private KeyHolder keyHolderMock;

  @Autowired
  @Qualifier("universityDaoImpl")
  @InjectMocks
  private UniversityDaoImpl universityDao;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testCreate_NullParameter() throws DaoLayerException {
    // Given
    final UniversityDTO dummyDTO = null;
    final UniversityDTO expectedDTO = null;

    // When
    final UniversityDTO actualDTO = universityDao.create(dummyDTO);

    // Then
    assertThat(actualDTO, is(expectedDTO));
  }

  @Test
  public void testCreate_EmptyParameter() throws DaoLayerException {
    // Given
    final UniversityDTO dummyDTO = new UniversityDTO();
    final UniversityDTO expectedDTO = null;

    // When
    final UniversityDTO actualDTO = universityDao.create(dummyDTO);

    // Then
    assertThat(actualDTO, is(expectedDTO));
  }

  @Test
  public void testCreate_NullUniversityStatusDTO() throws Exception {
    // Given
    final UniversityDTO universityDTO = new UniversityDTO();
    universityDTO.setName("Test");
    universityDTO.setDescription("Test");
    universityDTO.setFoundationDate(LocalDate.of(1966, 3, 17));
    final UniversityStatusDTO expectedUniversityStatusDTO = new UniversityStatusDTO();
    expectedUniversityStatusDTO.setId((long) UniversityStatus.PENDING_GOVERNMENT_APPROVAL.ordinal() + 1);
    expectedUniversityStatusDTO.setStatusName(UniversityStatus.PENDING_GOVERNMENT_APPROVAL);
    final LocalDateTime returnedTime = LocalDateTime.of(2017, 10, 5, 1, 15);
    final UniversityDTO expectedDTO = new UniversityDTO();
    expectedDTO.setId(1L);
    expectedDTO.setName("Test");
    expectedDTO.setDescription("Test");
    expectedDTO.setFoundationDate(LocalDate.of(1966, 3, 17));
    expectedDTO.setUniversityStatusDTO(expectedUniversityStatusDTO);
    expectedDTO.setCreationInDB(returnedTime);
    expectedDTO.setLastUpdateInDB(returnedTime);
    final Map<String, Object> returnedKeys = new HashMap<>();
    returnedKeys.put("UNVR_ID", 1L);
    mockStatic(LocalDateTime.class);

    // When
    when(LocalDateTime.now()).thenReturn(returnedTime);
    when(keyHolderMock.getKeys()).thenReturn(returnedKeys);
    final UniversityDTO actualDTO = universityDao.create(universityDTO);

    // Then
    assertThat(actualDTO, is(expectedDTO));
  }

  @Test
  public void testCreate_CorrectParameter() throws Exception {
    // Given
    final UniversityStatusDTO universityStatusDTO = new UniversityStatusDTO();
    universityStatusDTO.setId((long) UniversityStatus.OPEN.ordinal() + 1);
    universityStatusDTO.setStatusName(UniversityStatus.OPEN);
    final UniversityDTO universityDTO = new UniversityDTO();
    universityDTO.setName("Test");
    universityDTO.setDescription("Test");
    universityDTO.setFoundationDate(LocalDate.of(1966, 3, 17));
    universityDTO.setUniversityStatusDTO(universityStatusDTO);
    final LocalDateTime returnedTime = LocalDateTime.of(2017, 10, 5, 1, 15);
    final UniversityDTO expectedDTO = new UniversityDTO();
    expectedDTO.setId(1L);
    expectedDTO.setName("Test");
    expectedDTO.setDescription("Test");
    expectedDTO.setFoundationDate(LocalDate.of(1966, 3, 17));
    expectedDTO.setUniversityStatusDTO(universityStatusDTO);
    expectedDTO.setCreationInDB(returnedTime);
    expectedDTO.setLastUpdateInDB(returnedTime);
    final Map<String, Object> returnedKeys = new HashMap<>();
    returnedKeys.put("UNVR_ID", 1L);
    mockStatic(LocalDateTime.class);

    // When
    when(LocalDateTime.now()).thenReturn(returnedTime);
    when(keyHolderMock.getKeys()).thenReturn(returnedKeys);
    final UniversityDTO actualDTO = universityDao.create(universityDTO);

    // Then
    assertThat(actualDTO, is(expectedDTO));
  }

  @Test
  public void testFindById_NullId() {
    // Given
    final Long dummyId = null;
    final UniversityDTO expectedDTO = null;


    // When
    final UniversityDTO actualDTO = universityDao.findById(dummyId);

    // Then
    assertThat(actualDTO, is(expectedDTO));
  }

  @Test
  public void testFindById_NotExistingId() {
    // Given
    final Long notExistingId = -100L;
    final UniversityDTO expectedDTO = null;

    // When
    final UniversityDTO actualDTO = universityDao.findById(notExistingId);

    // Then
    assertThat(actualDTO, is(expectedDTO));
  }

  @Test
  public void testFindById_ExistingId() {
    // Given
    final Long existingId = 1L;
    final UniversityDTO expectedDTO = new UniversityDTO();
    expectedDTO.setId(existingId);
    expectedDTO.setName("Test");
    expectedDTO.setDescription("Test");
    expectedDTO.setFoundationDate(LocalDate.of(1888, 8, 8));
    final UniversityStatusDTO statusDTO = new UniversityStatusDTO();
    statusDTO.setId((long) (UniversityStatus.OPEN.ordinal() + 1));
    statusDTO.setStatusName(UniversityStatus.OPEN);
    expectedDTO.setUniversityStatusDTO(statusDTO);
    expectedDTO.setCreationInDB(LocalDateTime.of(2017, 10, 5, 2, 30));
    expectedDTO.setLastUpdateInDB(LocalDateTime.of(2017, 10, 5, 2, 30));

    // When
    final UniversityDTO actualDTO = universityDao.findById(existingId);

    // Then
    assertThat(actualDTO, is(expectedDTO));
  }

  @Test
  public void testUpdate_NullParameter() throws DaoLayerException {
    // Given
    final UniversityDTO dummyDTO = null;
    final UniversityDTO expectedDTO = null;

    // When
    final UniversityDTO actualDTO = universityDao.update(dummyDTO);

    // Then
    assertThat(actualDTO, is(expectedDTO));
  }

  @Test
  public void testUpdate_EmptyParameter() throws DaoLayerException {
    // Given
    final UniversityDTO dummyDTO = new UniversityDTO();
    final UniversityDTO expectedDTO = null;

    // When
    final UniversityDTO actualDTO = universityDao.update(dummyDTO);

    // Then
    assertThat(actualDTO, is(expectedDTO));
  }

  @Test
  public void testUpdate_NotExistingIdParameter() throws DaoLayerException {
    // Given
    final UniversityDTO withNotExistingIdDTO = new UniversityDTO();
    withNotExistingIdDTO.setId(-100L);
    final UniversityDTO expectedDTO = null;

    // When
    final UniversityDTO actualDTO = universityDao.update(withNotExistingIdDTO);

    // Then
    assertThat(actualDTO, is(expectedDTO));
  }

  @Test
  public void testUpdate_CorrectParameter() throws DaoLayerException {
    // Given
    final UniversityDTO correctDTO = new UniversityDTO();
    correctDTO.setId(1L);
    correctDTO.setName("Test");
    correctDTO.setDescription("Test");
    correctDTO.setFoundationDate(LocalDate.of(1888, 8, 8));
    final UniversityStatusDTO statusDTO = new UniversityStatusDTO();
    statusDTO.setId((long) (UniversityStatus.OPEN.ordinal() + 1));
    statusDTO.setStatusName(UniversityStatus.OPEN);
    correctDTO.setUniversityStatusDTO(statusDTO);
    final UniversityDTO expectedDTO = new UniversityDTO();
    expectedDTO.setId(1L);
    expectedDTO.setName("Test");
    expectedDTO.setDescription("Test");
    expectedDTO.setFoundationDate(LocalDate.of(1888, 8, 8));
    expectedDTO.setUniversityStatusDTO(statusDTO);
    expectedDTO.setCreationInDB(LocalDateTime.of(2017, 10, 5, 2, 30));
    expectedDTO.setLastUpdateInDB(LocalDateTime.of(2017, 10, 5, 2, 30));

    // When
    final UniversityDTO actualDTO = universityDao.update(correctDTO);

    // Then
    assertThat(actualDTO, is(expectedDTO));
  }
}