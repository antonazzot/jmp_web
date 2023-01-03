package com.tsyrkunou.jmpwep.application.globalconfig;

import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.JOptionPane;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.tsyrkunou.jmpwep.application.model.amounts.customerbalance.Amount;
import com.tsyrkunou.jmpwep.application.model.order.OrderResponse;

public class OnProdCond implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        OrderResponse orderResponse = new OrderResponse();
        softEx(t -> orderResponse("s")).apply(new Amount());
        return JOptionPane.showConfirmDialog(null, "start?") == 0;
    }

    private <T> Function<Amount, OrderResponse> softEx(Consumer<Amount> func) {
        return arg -> {
            func.accept(arg);
            return new OrderResponse();
        };
    }

    private OrderResponse orderResponse(String s) {
        return new OrderResponse();
    }
}
//    softEx(t -> ).apply(new OrderResponse());
//
//        package com.epam.autocode.courses.service.course.autocodebot;
//
//        import com.epam.autocode.common.i18n.MessageResolver;
//        import com.epam.autocode.courses.model.course.Course;
//        import com.epam.autocode.courses.service.structure.task.service.CourseTaskService;
//        import com.epam.autocode.courses.web.course.model.request.BotPermissionLevelRequest;
//        import com.epam.autocode.git.integration.exception.GitException;
//        import com.epam.autocode.git.integration.external.model.GitRepositoryData;
//        import com.epam.autocode.courses.model.structure.task.CourseTask;
//        import com.epam.autocode.courses.service.course.relation.CourseAccess;
//        import com.epam.autocode.courses.service.course.search.CourseSearchService;
//        import com.epam.autocode.git.integration.model.CollaboratorPermissionLevel;
//        import com.epam.autocode.security.model.User;
//        import com.epam.autocode.tasks.logging.TasksLogging;
//        import com.epam.autocode.tasks.service.assignment.accessor.ChangeAccessGitRepositoryResult;
//        import com.epam.autocode.tasks.service.task.details.coderepository.CodeRepositoryDataResolver;
//        import com.epam.autocode.tasks.service.task.details.coderepository.CodeRepositoryTaskDetails;
//        import lombok.RequiredArgsConstructor;
//        import lombok.extern.slf4j.Slf4j;
//        import org.apache.commons.collections4.CollectionUtils;
//        import org.springframework.scheduling.annotation.Async;
//        import org.springframework.stereotype.Service;
//
//        import java.util.Collection;
//        import java.util.List;
//        import java.util.Map;
//        import java.util.Objects;
//        import java.util.Optional;
//        import java.util.function.Consumer;
//        import java.util.function.Function;
//
//        import static com.epam.autocode.common.exception.ExceptionUtils.getMessageChain;
//        import static com.epam.autocode.courses.logging.CoursesLogging.DETAILS;
//        import static java.util.stream.Collectors.partitioningBy;
//        import static net.logstash.logback.argument.StructuredArguments.kv;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class AutocodeBotService {
//
//    private final CourseSearchService courseSearchService;
//    private final CourseTaskService courseTaskService;
//    private final CodeRepositoryDataResolver codeRepositoryDataResolver;
//    private final CourseAccess courseAccess;
//    private final MessageResolver messageResolver;
//
//
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public void addAutocodeBotToAllRepository(Long courseId, BotPermissionLevelRequest botPermissionLevelRequest) {
//        Course course = courseSearchService.findById(courseId);
//        Collection<CourseTask> tasks = courseTaskService.findAll(course);
//
//        if (!CollectionUtils.isEmpty(tasks)) {
//
//            Map<Boolean, List<RepositoryBotAddResult>> results = tasks.stream()
//                    .filter(task -> task.getDetails() instanceof CodeRepositoryTaskDetails)
//                    .collect(toMap(task -> task, task ->
//                            List.of(((CodeRepositoryTaskDetails) task.getDetails()).getTaskRepository(),
//                                    ((CodeRepositoryTaskDetails) task.getDetails()).getTestsRepository())))
//                    .entrySet().stream()
//                    .map(mapTaskRepos -> {
//                        return mapTaskRepos.getValue().stream().map(
//                                gitRepositoryData -> {
//                                    User repositoryOwner = courseAccess.getRepositoryOwner(course, mapTaskRepos.getKey());
//                                    var result = softAddAutocodeBot(gitRepositoryData, repositoryOwner.getId(), botPermissionLevelRequest.getPermissionLevel());
//                                    log.info(AUTOCODEBOT_ADDED, kv(TasksLogging.DETAILS, CoursesLogging.AddAutocodeBotRepositoryDetails.from(mapTaskRepos.getKey(), result)));
//                                    return result;
//                                }
//                        ).findFirst();
//                    }).filter(Optional::isPresent)
//                    .map(Optional::get)
//                    .collect(partitioningBy(RepositoryBotAddResult::isSuccess));
//
//            eventBus.publishEvent(new AutocodeBotAddEvent(1L, results));
//
//        }
//    }
//
//    @Async
//    public void getTaskAssignmentsRepository(Long courseId, BotPermissionLevelRequest botPermissionLevelRequest) {
//        Course course = courseSearchService.findById(courseId);
//        Collection<CourseTask> tasks = courseTaskService.findAll(course);
//
//        if (!CollectionUtils.isEmpty(tasks)) {
//
//            Map<Boolean, List<RepositoryBotAddResult>> results = tasks.stream()
//                    .map(task -> {
//
//                        var taskRepository = details.getTaskRepository();
//                        var testRepository = details.getTestsRepository();
//                        User repositoryOwner = courseAccess.getRepositoryOwner(course, task);
//                        var result = softAddAutocodeBot(taskRepository, repositoryOwner.getId(), botPermissionLevelRequest.getPermissionLevel());
//
//
//                        log.info(AUTOCODEBOT_ADDED, kv(TasksLogging.DETAILS, AddAutocodeBotRepositoryDetails.from(task, result)));
//                        return result;
//                    })
//                    .filter(Objects::nonNull)
//                    .collect(partitioningBy(ChangeAccessGitRepositoryResult::isSuccess));
//
//        }
//    }
//
//    private RepositoryBotAddResult softAddAutocodeBot(GitRepositoryData gitRepositoryData, Long ownerId,
//                                                      CollaboratorPermissionLevel permissionLevel) {
//        return softExecution(gitRepositoryData,
//                t -> addAutocodeBot(gitRepositoryData, ownerId, permissionLevel)
//        ).apply(gitRepositoryData);
//    }
//
//    private <T> Function<T, RepositoryBotAddResult> softExecution(GitRepositoryData gitRepositoryData, Consumer<T> func) {
//        return arg -> {
//            var result = new RepositoryBotAddResult();
//            result.setRepositoryLink(gitRepositoryData.getGitUrl());
//            try {
//                func.accept(arg);
//                result.setSuccess(true);
//            } catch (GitException e) {
//                result.setSuccess(false);
//                result.setMessage(messageResolver.resolve(e.getCode(), e.getArgs()));
//            } catch (Exception e) {
//                result.setSuccess(false);
//                log.error("Unexpected exception: '{}'", getMessageChain(e), e);
//                result.setMessage(messageResolver.resolve("error.unknown"));
//            }
//            return result;
//        };
//    }
//
//    private void addAutocodeBot(GitRepositoryData gitRepositoryData, Long ownerId,
//                                CollaboratorPermissionLevel permissionLevel) {
//        Optional.ofNullable(gitRepositoryData)
//                .ifPresent(repositoryData ->
//                        codeRepositoryDataResolver.addAutocodeBot(repositoryData, ownerId, permissionLevel));
//    }
//
//
//
//
//
//
//
//    public void addAutocodeBotToAllRepository(Long courseId, BotPermissionLevelRequest botPermissionLevelRequest) {
//        Course course = courseSearchService.findById(courseId);
//        Collection<CourseTask> tasks = courseTaskService.findAll(course);
//        for (CourseTask task : tasks) {
//            if (task.getDetails() instanceof CodeRepositoryTaskDetails details) {
//                User repositoryOwner = courseAccess.getRepositoryOwner(course, task);
//                codeRepositoryDataResolver.addAutocodeBot(details.getTestsRepository(), repositoryOwner.getId(), botPermissionLevelRequest.getPermissionLevel());
//                if (details.getTestsRepository() != null) {
//                    codeRepositoryDataResolver.addAutocodeBot(details.getTestsRepository(), repositoryOwner.getId(), botPermissionLevelRequest.getPermissionLevel());
//                }
//            }
//        }
//    }
//}
