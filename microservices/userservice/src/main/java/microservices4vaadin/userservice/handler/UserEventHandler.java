package microservices4vaadin.userservice.handler;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventhandling.replay.ReplayAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import microservices4vaadin.userevents.UserCreatedEvent;
import microservices4vaadin.userservice.persistence.User;
import microservices4vaadin.userservice.repository.UserRepository;

@Component
@Slf4j
public class UserEventHandler implements ReplayAware {

    @Autowired
    private UserRepository userRepository;

    @EventHandler
    public void handle(UserCreatedEvent event) {
        log.info("UserCreatedEvent: [{}] '{}'", event.getId(), event.getEmail());
        User user = new User();
        user.setItemId(event.getId());
        user.setFirstName(event.getFirstName());
        user.setLastName(event.getLastName());
        user.setLanguage("en");
        userRepository.save(user);
    }

    public void beforeReplay() {
        log.info("Event Replay is about to START. Clearing the View...");
    }

    public void afterReplay() {
        log.info("Event Replay has FINISHED.");
    }

    public void onReplayFailed(Throwable cause) {
        log.error("Event Replay has FAILED.");
    }

}
