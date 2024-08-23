package com.example.coursecanvasspring.entity.chapter;
import static com.example.coursecanvasspring.constants.StringConstants.*;

public class ChapterFactory {
    Chapter createChapter(String chapterType) {
        if (chapterType == null) {
            return null;
        } else if (chapterType.equalsIgnoreCase(CHAPTER_TYPE_VIDEO)) {
            return new VideoChapter();
        } else if (chapterType.equalsIgnoreCase(CHAPTER_TYPE_DOCUMENT)) {
            return new DocumentChapter();
        } else if (chapterType.equalsIgnoreCase(CHAPTER_TYPE_QUIZ)) {
            return new QuizChapter();
        } else if (chapterType.equalsIgnoreCase(CHAPTER_TYPE_ASSIGNMENT)) {
            return new AssignmentChapter();
        } else if (chapterType.equalsIgnoreCase(CHAPTER_TYPE_CODE)) {
            return new CodeChapter();
        } else {
            return null;
        }
    }
}
