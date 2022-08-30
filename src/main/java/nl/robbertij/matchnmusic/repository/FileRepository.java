package nl.robbertij.matchnmusic.repository;

import nl.robbertij.matchnmusic.model.file.FileDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface FileRepository extends JpaRepository<FileDocument, Long> {
    FileDocument findByFileName(String fileName);
}
