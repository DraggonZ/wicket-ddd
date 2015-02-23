package promolo.wicket.account.ui;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

import javax.annotation.Nonnull;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import promolo.wicket.account.application.AccountApplicationService;
import promolo.wicket.account.application.ChangeAccountPersonCommand;
import promolo.wicket.account.domain.Account;
import promolo.wicket.account.domain.AccountPersonChanged;
import promolo.wicket.account.domain.Person;
import promolo.wicket.core.application.ApplicationCommandExecutor;
import promolo.wicket.core.domain.DomainEventPublisher;

public class AccountPresenterTest {

    @Mock
    private AccountApplicationService accountApplicationService;

    @Mock
    private ApplicationCommandExecutor applicationCommandExecutor;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        doAnswer(new Answer() {

            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Person person = person();
                DomainEventPublisher.instance().publish(
                        new AccountPersonChanged(accountId(), person.title(), person.firstName(), person.middleName(), person.lastName()));
                return null;
            }

        }).when(applicationCommandExecutor()).execute(any(ChangeAccountPersonCommand.class));
        when(accountApplicationService().findAccountById(eq(accountId()))).thenReturn(new Account(accountId(), person()));
    }

    @AfterMethod
    public void tearDown() {
        DomainEventPublisher.instance().reset();
    }

    @Test
    public void toggleTitleAutoGeneration() throws Exception {
        AccountView view = mock(AccountView.class);
        AccountPresenter presenter = newPresenter(view);
        assertFalse(presenter.isTitleAutoGenerationEnabled());
        presenter.toggleTitleAutoGeneration();
        assertTrue(presenter.isTitleAutoGenerationEnabled());
        verify(view).titleAutoGeneratorStateChanged(eq(true));
    }

    @Test
    public void notifyPersonNameChanged_autoTitle() throws Exception {
        AccountView view = mock(AccountView.class);
        AccountPresenter presenter = newPresenter(view);
        presenter.toggleTitleAutoGeneration();
        presenter.getCommand().setLastName("Фио");
        presenter.onPersonNameUpdated();
        assertEquals(presenter.getCommand().getTitle(), "Фио Имя Отчество");
        verify(view).accountTitleGenerated();
    }

    @Test
    public void notifyPersonNameChanged_manualTitle() throws Exception {
        AccountView view = mock(AccountView.class);
        AccountPresenter presenter = newPresenter(view);
        presenter.getCommand().setLastName("Фио");
        presenter.onPersonNameUpdated();
        assertEquals(presenter.getCommand().getTitle(), "Фамилия Имя Отчество");
        verifyZeroInteractions(view);
    }

    @Test
    public void notifySaveAccountChanges() throws Exception {
        AccountView view = mock(AccountView.class);
        AccountPresenter presenter = newPresenter(view);
        presenter.getCommand().setFirstName("И");
        presenter.getCommand().setMiddleName("О");
        presenter.getCommand().setLastName("Ф");
        presenter.onSaveAccountChanges();

        ArgumentCaptor<ChangeAccountPersonCommand> argumentCaptor = ArgumentCaptor.forClass(ChangeAccountPersonCommand.class);
        verify(applicationCommandExecutor()).execute(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue().getTitle(), "Фамилия Имя Отчество");

        verify(view).accountPersonChanged();
    }

    @Nonnull
    private AccountPresenter newPresenter(@Nonnull AccountView view) {
        AccountPresenter presenter = new AccountPresenter(view, accountId());
        presenter.setAccountApplicationService(accountApplicationService());
        presenter.setApplicationCommandExecutor(applicationCommandExecutor());
        presenter.refreshModel();
        return presenter;
    }

    @Nonnull
    private AccountApplicationService accountApplicationService() {
        return this.accountApplicationService;
    }

    @Nonnull
    private ApplicationCommandExecutor applicationCommandExecutor() {
        return this.applicationCommandExecutor;
    }

    @Nonnull
    private static String accountId() {
        return "user";
    }

    @Nonnull
    private static Person person() {
        return new Person("Имя", "Отчество", "Фамилия");
    }

}