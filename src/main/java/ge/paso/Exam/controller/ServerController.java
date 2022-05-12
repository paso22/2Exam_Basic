package ge.paso.Exam.controller;

import ge.paso.Exam.Service.ServerService;
import ge.paso.Exam.dto.ServerCreationDto;
import ge.paso.Exam.entities.Server;
import ge.paso.Exam.repositories.ServerRepository;
import ge.paso.Exam.users.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/server")
@AllArgsConstructor
public class ServerController {

    private final ServerService serverService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public ResponseEntity<String> logIn() {
        return new ResponseEntity<>("You've logged in!", HttpStatus.OK);
    }

    @PostMapping("/create/{userName}")
    public ResponseEntity<String> createServer(@Valid @RequestBody ServerCreationDto serverCreationDto, @PathVariable String userName, BindingResult b) {
        serverService.createServer(serverCreationDto, userName);
        return new ResponseEntity<>("server with name - " + serverCreationDto.getName() + " created", HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Server>> getAllFreeServers() {
        return new ResponseEntity<>(serverService.getFreeServers(), HttpStatus.OK);
    }

    @GetMapping("/choose/{userName}/{serverName}")
    public ResponseEntity<Server> chooseServer(@PathVariable String userName, @PathVariable String serverName) {
        return new ResponseEntity<>(serverService.chooseServer(userName,serverName), HttpStatus.OK);
    }

    @GetMapping("/release/{userName}/{serverName}")
    public ResponseEntity<String> releaseServer(@PathVariable String userName, @PathVariable String serverName) {
        serverService.releaseServer(userName,serverName);
        return new ResponseEntity<>("Server with name - " + serverName + " was released by user - " + userName, HttpStatus.OK);
    }

    @GetMapping("/deleteUser/{superAdminName}/{toDeleteUser}")
    public ResponseEntity<String> deleteUser(@PathVariable String superAdminName, @PathVariable String toDeleteUser) {
        serverService.deleteUser(superAdminName, toDeleteUser);
        return new ResponseEntity<>("username - " + toDeleteUser + " was deleted by super admin - " + superAdminName, HttpStatus.OK);
    }

    @GetMapping("/changeRole/{superAdminName}/{secondUser}/{role}")
    public ResponseEntity<String> changeRole(@PathVariable String superAdminName, @PathVariable String secondUser, @PathVariable String role) {
        serverService.changeRole(superAdminName, secondUser, role);
        return new ResponseEntity<>("username - " + secondUser + "'s role was changed by super admin - " + superAdminName, HttpStatus.OK);
    }

}
