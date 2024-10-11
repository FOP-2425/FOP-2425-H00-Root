plugins {
 alias(libs.plugins.algomate)
    alias(libs.plugins.jagr)
    alias(libs.plugins.style)
}

version = file("version").readLines().first()

exercise {
    assignmentId.set("h00")
}

submission {
    // ACHTUNG!
    // Setzen Sie im folgenden Bereich Ihre TU-ID (NICHT Ihre Matrikelnummer!), Ihren Nachnamen und Ihren Vornamen
    // in Anführungszeichen (z.B. "ab12cdef" für Ihre TU-ID) ein!
    studentId = "ab12cdef"
    firstName = "sol_first"
    lastName = "sol_last"

    // Optionally require own tests for mainBuildSubmission task. Default is false
    requireTests = false
}

dependencies {
    implementation(libs.fopbot)
}

configurations.all {
    resolutionStrategy {
        configurations.all {
            resolutionStrategy {
                force(
                    libs.algoutils.student,
                    libs.algoutils.tutor,
                )
            }
        }
    }
}

jagr {
    graders {
        val graderPrivate by creating {
            graderName.set("H00-Private")
            rubricProviderName.set("h00.H00_RubricProvider")
            configureDependencies {
                implementation(libs.algoutils.tutor)
            }
        }
    }
}
