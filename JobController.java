package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        /** DONE */
        Job displayJob = jobData.findById(id);
        model.addAttribute("job", displayJob);

        return "job-detail";
    }


    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }


    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        String name;
        Employer employer;
        Location location;
        PositionType positionType;
        CoreCompetency coreCompetency;

        if (errors.hasErrors()) {
            return "new-job";
        }

        // get employer using employer id
        Employer formEmployer = null;
        for (Employer aEmployer : jobForm.getEmployers()) {
            if (aEmployer.getId() == jobForm.getEmployerId()) {
                formEmployer = aEmployer;
            }
        }

        name = jobForm.getName();
        employer = formEmployer;
        location = new Location(jobForm.getLocation());
        positionType = new PositionType(jobForm.getPositionType());
        coreCompetency = new CoreCompetency(jobForm.getCoreCompetency());

        // create job object
        Job newJob = new Job(name, employer, location,
                positionType, coreCompetency);

        jobData.add(newJob);
        model.addAttribute("job", newJob);

        return "job-detail";
    }
}