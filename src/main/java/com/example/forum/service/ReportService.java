package com.example.forum.service;

import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.ReportRepository;
import com.example.forum.repository.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    /*
     * レコード全件取得処理
     */
    public List<ReportForm> findAllReport(String startDate, String endDate) throws ParseException {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //現在日時を取得
        LocalDateTime now = LocalDateTime.now();

        //フォーマットの指定
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        String defaultStart = "2020/01/01 00:00:00";
        String defaultEnd = now.format(formatter);
        if (StringUtils.hasText(startDate)) {
            startDate = startDate.replace("-", "/");
            startDate = startDate + " 00:00:00";
        } else {
            startDate = defaultStart;
        }
        if (StringUtils.hasText(endDate)) {
            endDate = endDate.replace("-", "/");
            endDate = endDate + " 23:59:59";
        } else {
            endDate = defaultEnd;
        }
        Date start = sdFormat.parse(startDate);
        Date end = sdFormat.parse(endDate);
        List<Report> results = reportRepository.
                findByCreatedDateBetweenOrderByUpdatedDateDesc(start, end);
        List<ReportForm> reports = setReportForm(results);

        return reports;
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private List<ReportForm> setReportForm(List<Report> results) {
        List<ReportForm> reports = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            ReportForm report = new ReportForm();
            Report result = results.get(i);
            report.setId(result.getId());
            report.setContent(result.getContent());
            report.setCreatedDate(result.getCreatedDate());
            report.setUpdatedDate(result.getUpdatedDate());
            reports.add(report);
        }
        return reports;
    }

    /*
     * レコード追加
     */
    public void saveReport(ReportForm reqReport) {
        Report saveReport = setReportEntity(reqReport);
        reportRepository.save(saveReport);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Report setReportEntity(ReportForm reqReport) {
        Report report = new Report();
        report.setId(reqReport.getId());
        report.setContent(reqReport.getContent());

        Date now = new Date();
        //idが0より大きい場合は「編集(更新)」処理
        if (reqReport.getId() > 0) {
            //データベースから元のデータを取得
            Report update = reportRepository.findById(reqReport.getId()).orElse(null);
            if (update != null) {
                //作成日を引き継ぐ
                report.setCreatedDate(update.getCreatedDate());
            } else {
                //現在時刻を作成日に入れる
                report.setCreatedDate(now);
            }
            //現在の時刻をセットする
            report.setUpdatedDate(now);
        } else {
            //idが0以下の場合新規投稿処理をし、
            //作成日と更新日に現在時刻を格納する
            report.setCreatedDate(now);
            report.setUpdatedDate(now);
        }

        return report;
    }

    /*
     * レコード削除
     */
    public void deleteReport(Integer id) {
        reportRepository.deleteById(id);
    }

    /*
     * レコード1件取得
     */
    public ReportForm editReport(Integer id) {
        List<Report> results = new ArrayList<>();
        results.add(reportRepository.findById(id).orElse(null));
        List<ReportForm> reports = setReportForm(results);
        return reports.get(0);
    }

}